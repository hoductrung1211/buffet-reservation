package com.example.demo.service;

import com.example.demo.dto.bill.BillView;
import com.example.demo.dto.bill.CUBillReq;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.auth.Employee;
import com.example.demo.model.bill.Bill;
import com.example.demo.model.bill.TableHistory;
import com.example.demo.model.bill.TableHistoryMenuItem;
import com.example.demo.model.bill.TableHistoryStatus;
import com.example.demo.repository.IBillRepository;
import com.example.demo.repository.ITableHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {
    private final IBillRepository billRepository;
    private final ModelMapper modelMapper;
    private final ContextHolderService contextHolderService;
    private final ITableHistoryRepository tableHistoryRepository;

    @Autowired
    public BillService(IBillRepository billRepository, ModelMapper modelMapper, ContextHolderService contextHolderService, ITableHistoryRepository tableHistoryRepository) {
        this.billRepository = billRepository;
        this.modelMapper = modelMapper;
        this.contextHolderService = contextHolderService;
        this.tableHistoryRepository = tableHistoryRepository;
    }

    @Transactional
    public ResponseEntity<?> createBill(CUBillReq cuBillReq) {
        Employee employee = (Employee) contextHolderService.getObjectFromContext();
        TableHistory tableHistory = tableHistoryRepository.findById(cuBillReq.getTableHistoryId()).get();
        BigDecimal priceTicket = BigDecimal.valueOf(tableHistory.getAdultsQuantity())
                .multiply(tableHistory.getPrice().getAdultPrice())
                .add(BigDecimal.valueOf(tableHistory.getChildrenQuantity())
                        .multiply(tableHistory.getPrice().getChildPrice()));
        BigDecimal priceOrder = BigDecimal.valueOf(0.0);
        for (TableHistoryMenuItem order : tableHistory.getMenuItems()){
            priceOrder = priceOrder.add(order.getPriceMenuItem().getPrice()
                    .multiply(BigDecimal.valueOf(order.getMenuItemQuantity()))
            );
        }
        BigDecimal total = priceTicket.add(priceOrder);
        Bill bill = new Bill(employee,tableHistory,10.0,cuBillReq.getNote(),total);
        tableHistory.setEndDateTime(LocalDateTime.now());
        tableHistory.setTableHistoryStatus(TableHistoryStatus.FINISHED);
        try {
            bill = billRepository.save(bill);
            tableHistoryRepository.save(tableHistory);
            return ResponseEntity.ok(modelMapper.map(bill, BillView.class));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }


    public ResponseEntity<?> getAllByDate(LocalDate date) {
        Object object = contextHolderService.getObjectFromContext();
        List<BillView> billViews;
        if(object instanceof Customer){
            if(date == null) billViews = billRepository.findAllByCreateDateOfCustomer(date,((Customer) object).getCustomerId())
                    .stream().map(bill -> modelMapper.map(bill,BillView.class))
                    .collect(Collectors.toList());
            else billViews = billRepository.findAllByCustomer(((Customer) object).getCustomerId())
                    .stream().map(bill -> modelMapper.map(bill,BillView.class))
                    .collect(Collectors.toList());
        }else {
            if(date == null)  billViews = billRepository.findAllByCreateDate(date)
                    .stream().map(bill -> modelMapper.map(bill,BillView.class))
                    .collect(Collectors.toList());
            else billViews = billRepository.findAll()
                    .stream().map(bill -> modelMapper.map(bill,BillView.class))
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(billViews);
    }

    public ResponseEntity<?> getAll() {
        Object object = contextHolderService.getObjectFromContext();
        List<BillView> billViews;
        if(object instanceof Customer){
            billViews = billRepository.findAllByCustomer(((Customer) object).getCustomerId())
                    .stream().map(bill -> modelMapper.map(bill,BillView.class))
                    .collect(Collectors.toList());
        }else {
            billViews = billRepository.findAll()
                    .stream().map(bill -> modelMapper.map(bill,BillView.class))
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(billViews);
    }
}
