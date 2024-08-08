package com.example.demo.service;

import com.example.demo.dto.order.OrderView;
import com.example.demo.dto.tablehistory.CUTableHistoryReq;
import com.example.demo.dto.tablehistory.TableHistoryView;
import com.example.demo.dto.tablehistory.UMenuTableHistoryReq;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.auth.Employee;
import com.example.demo.model.bill.TableHistory;
import com.example.demo.model.bill.TableHistoryMenuItem;
import com.example.demo.model.bill.TableHistoryStatus;
import com.example.demo.model.menu.MenuItem;
import com.example.demo.model.menu.PriceMenuItem;
import com.example.demo.model.price.Price;
import com.example.demo.model.table.BuffetTable;
import com.example.demo.repository.IBuffetTableRepository;
import com.example.demo.repository.IPriceMenuItemRepository;
import com.example.demo.repository.ITableHistoryMenuItemRepository;
import com.example.demo.repository.ITableHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableHistoryService {
    private final ITableHistoryRepository tableHistoryRepository;
    private final IBuffetTableRepository buffetTableRepository;
    private final ContextHolderService contextHolderService;
    private final ModelMapper modelMapper;
    private final PriceService priceService;
    private final ITableHistoryMenuItemRepository tableHistoryMenuItemRepository;
    private final IPriceMenuItemRepository priceMenuItemRepository;

    @Autowired
    public TableHistoryService(ITableHistoryRepository tableHistoryRepository, IBuffetTableRepository buffetTableRepository, ContextHolderService contextHolderService, ModelMapper modelMapper, PriceService priceService, ITableHistoryMenuItemRepository tableHistoryMenuItemRepository, IPriceMenuItemRepository priceMenuItemRepository) {
        this.tableHistoryRepository = tableHistoryRepository;
        this.buffetTableRepository = buffetTableRepository;
        this.contextHolderService = contextHolderService;
        this.modelMapper = modelMapper;
        this.priceService = priceService;
        this.tableHistoryMenuItemRepository = tableHistoryMenuItemRepository;
        this.priceMenuItemRepository = priceMenuItemRepository;
    }

    @Transactional
    public ResponseEntity<?> createTableHistory(CUTableHistoryReq cuTableHistoryReq) {
        LocalDateTime time = LocalDateTime.now();
        TableHistory tableHistory = new TableHistory();
        BuffetTable buffetTable = getBuffetTableFreeInDate(cuTableHistoryReq.getAdultsQuantity()+cuTableHistoryReq.getChildrenQuantity(), time);
        if(buffetTable == null)
            return ResponseEntity.badRequest().body("No table found for the required number of people");
        tableHistory.setBuffetTable(buffetTable);
        // price add (not yet)
        Price price = priceService.getPriceTicketToDate(time.toLocalDate());
        if(price == null)
            return ResponseEntity.badRequest().body("No price found for date regis");
        tableHistory.setPrice(price);

        Employee employee = (Employee) contextHolderService.getObjectFromContext();
        tableHistory.setEmployee(employee);
        tableHistory.setAdultsQuantity(cuTableHistoryReq.getAdultsQuantity());
        tableHistory.setChildrenQuantity(cuTableHistoryReq.getChildrenQuantity());
        tableHistory.setStartDateTime(time);
        tableHistory.setTableHistoryStatus(TableHistoryStatus.SERVING);
        try {
            tableHistory = tableHistoryRepository.save(tableHistory);
            return ResponseEntity.ok(modelMapper.map(tableHistory, TableHistoryView.class));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Invalid table for this time");
        }
    }

    @Transactional
    public ResponseEntity<?> updateTableHistory(CUTableHistoryReq cuTableHistoryReq) {
        LocalDateTime time = LocalDateTime.now();
        TableHistory tableHistory = tableHistoryRepository.findById(cuTableHistoryReq.getTableHistoryId())
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
        if(!time.toLocalDate().isEqual(tableHistory.getReservation().getDate()))
            return ResponseEntity.badRequest().body("Today is not the check in date on the ticket");
        if (tableHistory.getTableHistoryStatus().equals(TableHistoryStatus.FINISHED)
                || tableHistory.getTableHistoryStatus().equals(TableHistoryStatus.CANCELED))
            return ResponseEntity.badRequest().body("FINISHED, CANCELED --> do not allow update");

        if(tableHistory.getReservation() != null
                && time.toLocalTime().isAfter(tableHistory.getReservation().getReservationTimeFrame().getEndTime())
                && time.toLocalDate().isEqual(tableHistory.getReservation().getDate())
        ) {
            return ResponseEntity.badRequest().body("Tooooooooooo late!");
        }

        Employee employee = (Employee) contextHolderService.getObjectFromContext();
        tableHistory.setEmployee(employee);
        tableHistory.setAdultsQuantity(cuTableHistoryReq.getAdultsQuantity());
        tableHistory.setChildrenQuantity(cuTableHistoryReq.getChildrenQuantity());
        tableHistory.setTableHistoryStatus(TableHistoryStatus.valueOf(cuTableHistoryReq.getStatus()));
        tableHistory.setStartDateTime(time);
        try {
            tableHistory = tableHistoryRepository.save(tableHistory);
            return ResponseEntity.ok(modelMapper.map(tableHistory, TableHistoryView.class));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("update is failed");
        }
    }

    private BuffetTable getBuffetTableFreeInDate(int totalPerson,LocalDateTime time) {
        List<BuffetTable> buffetTables = buffetTableRepository.findAllByQuantity(totalPerson);
        for (BuffetTable buffetTable : buffetTables){
            if(tableHistoryRepository.isHasSlotOnOff(time.toLocalDate(), time.toLocalTime(), time,buffetTable.getBuffetTableId())){
                return buffetTable;
            }
        }
        return null;
    }

    @Transactional
    public ResponseEntity<?> updateMenuInTableHistory(UMenuTableHistoryReq uMenuTableHistoryReq) {
        TableHistory tableHistory = tableHistoryRepository.findById(uMenuTableHistoryReq.getTableHistoryId())
                .orElseThrow(() -> new ResourceNotFoundException("not found"));

        if(!LocalDate.now().isEqual(tableHistory.getReservation().getDate()))
            return ResponseEntity.badRequest().body("Today is not the check in date on the ticket");

        if (tableHistory.getTableHistoryStatus().equals(TableHistoryStatus.FINISHED)
                || tableHistory.getTableHistoryStatus().equals(TableHistoryStatus.CANCELED))
            return ResponseEntity.badRequest().body("FINISHED, CANCELED --> do not allow order");

//        List<TableHistoryMenuItem> orderList = new ArrayList<>();
//        for (UMenuTableHistoryReq.Order order : uMenuTableHistoryReq.getOrderList()){
//            PriceMenuItem priceMenuItem = priceMenuItemRepository.findById(order.getPriceMenuItemId())
//                    .orElseThrow(() -> new ResourceNotFoundException("not found priceMenuItem"));
//            TableHistoryMenuItem tableHistoryMenuItem = new TableHistoryMenuItem(tableHistory,priceMenuItem,order.getMenuItemQuantity());
//            orderList.add(tableHistoryMenuItem);
//        }
        PriceMenuItem priceMenuItem = priceMenuItemRepository.findById(uMenuTableHistoryReq.getPriceMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("not found priceMenuItem"));
        TableHistoryMenuItem tableHistoryMenuItem = new TableHistoryMenuItem(tableHistory,priceMenuItem,uMenuTableHistoryReq.getMenuItemQuantity());
        try {
            tableHistoryMenuItem = tableHistoryMenuItemRepository.save(tableHistoryMenuItem);
//            orderList = tableHistoryMenuItemRepository.tableHistoryMenuItemRepository
//            List<OrderView> orderViews = orderList
//                    .stream()
//                    .map(order -> modelMapper.map(order, OrderView.class))
//                    .collect(Collectors.toList()
//                    );
//            return ResponseEntity.ok(orderViews);
            return ResponseEntity.ok(modelMapper.map(tableHistoryMenuItem,OrderView.class));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("order is failed");
        }
    }

    @Transactional
    public ResponseEntity<?> deleteMenuInTableHistory(Integer tableHistoryId, Integer priceMenuItemId) {
        TableHistoryMenuItem menuItem = tableHistoryMenuItemRepository.findByHisAndPrice(tableHistoryId,priceMenuItemId);
        if(!menuItem.getTableHistory().getTableHistoryStatus().equals(TableHistoryStatus.SERVING))
            return ResponseEntity.badRequest().body("status table history != SERVING so can't delete item");
        try {
            tableHistoryMenuItemRepository.delete(menuItem);
            return ResponseEntity.ok("delete is successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("delete is failed");
        }
    }

    public ResponseEntity<?> getDetail(Integer id) {
        TableHistory tableHistory = tableHistoryRepository.findByReservation_ReservationId(id);
        return ResponseEntity.ok(modelMapper.map(tableHistory, TableHistoryView.class));
    }

    public ResponseEntity<?> getOrders(Integer id) {
        List<TableHistoryMenuItem> menuItemList = tableHistoryMenuItemRepository.findAllByTableHistory_TableHistoryId(id);
        List<OrderView> orderViews = menuItemList
                    .stream()
                    .map(order -> modelMapper.map(order, OrderView.class))
                    .collect(Collectors.toList()
                    );
        return ResponseEntity.ok(orderViews);
    }

    public ResponseEntity<?> getTotal(Integer id) {
        List<TableHistoryMenuItem> menuItemList = tableHistoryMenuItemRepository.findAllByTableHistory_TableHistoryId(id);
        BigDecimal total = BigDecimal.valueOf(0);
        if(!menuItemList.isEmpty()){
            TableHistory tableHistory = menuItemList.get(0).getTableHistory();
            BigDecimal priceTicket = (tableHistory.getPrice().getAdultPrice().multiply(BigDecimal.valueOf(tableHistory.getAdultsQuantity())))
                    .add(tableHistory.getPrice().getChildPrice().multiply(BigDecimal.valueOf(tableHistory.getChildrenQuantity())));

            BigDecimal priceItem = BigDecimal.valueOf(0);
            for(TableHistoryMenuItem menuItem : menuItemList){
                priceItem = priceItem.add(menuItem.getPriceMenuItem().getPrice().multiply(BigDecimal.valueOf(menuItem.getMenuItemQuantity())));
            }
            total = priceTicket.add(priceItem);
        }
        return ResponseEntity.ok(total);
    }
}
