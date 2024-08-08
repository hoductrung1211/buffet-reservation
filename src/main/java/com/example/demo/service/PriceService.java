package com.example.demo.service;

import com.example.demo.dto.price.CUPriceMenuItemReq;
import com.example.demo.dto.price.CUPriceTicketReq;
import com.example.demo.dto.price.PriceMenuItemView;
import com.example.demo.dto.price.PriceView;
import com.example.demo.model.menu.MenuItem;
import com.example.demo.model.menu.PriceMenuItem;
import com.example.demo.model.price.DayGroup;
import com.example.demo.model.price.DayGroupName;
import com.example.demo.model.price.Holiday;
import com.example.demo.model.price.Price;
import com.example.demo.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceService {
    private final IPriceRepository priceRepository;
    private final IMenuItemRepository menuItemRepository;
    private final IPriceMenuItemRepository priceMenuItemRepository;
    private final ITableHistoryMenuItemRepository tableHistoryMenuItemRepository;
    private final ITableHistoryRepository tableHistoryRepository;
    private final IDayGroupRepository dayGroupRepository;
    private final IHolidayRepository holidayRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PriceService(IPriceRepository priceRepository, IMenuItemRepository menuItemRepository, IPriceMenuItemRepository priceMenuItemRepository, ITableHistoryMenuItemRepository tableHistoryMenuItemRepository, ITableHistoryRepository tableHistoryRepository, IDayGroupRepository dayGroupRepository, IHolidayRepository holidayRepository, ModelMapper modelMapper) {
        this.priceRepository = priceRepository;
        this.menuItemRepository = menuItemRepository;
        this.priceMenuItemRepository = priceMenuItemRepository;
        this.tableHistoryMenuItemRepository = tableHistoryMenuItemRepository;
        this.tableHistoryRepository = tableHistoryRepository;
        this.dayGroupRepository = dayGroupRepository;
        this.holidayRepository = holidayRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ResponseEntity<?> createPriceMenuItem(CUPriceMenuItemReq cuPriceMenuItemReq) {
        LocalDate curDate = LocalDate.now();
        if(cuPriceMenuItemReq.getApplicationDate().isBefore(curDate)) {
            return ResponseEntity.badRequest().body("Error");
        }
        MenuItem menuItem = menuItemRepository.findById(cuPriceMenuItemReq.getMenuItemId()).get();
        if (cuPriceMenuItemReq.getApplicationDate().isEqual(curDate)){
            if(priceMenuItemRepository.existsByApplicationDateAndMenuItem_MenuItemId(curDate,menuItem.getMenuItemId()))
                return ResponseEntity.badRequest().body("menu item has been set price in today, can't change price");
            if(tableHistoryMenuItemRepository.isHasTableOrderOnDate(menuItem.getMenuItemId(),curDate))
                return ResponseEntity.badRequest().body("Has table SERVING with this menu item, can't change price");
        }
        PriceMenuItem priceMenuItem = new PriceMenuItem(cuPriceMenuItemReq.getPrice(),cuPriceMenuItemReq.getApplicationDate(),menuItem);
        try {
            priceMenuItem = priceMenuItemRepository.save(priceMenuItem);
            return ResponseEntity.ok(modelMapper.map(priceMenuItem, PriceMenuItemView.class));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @Transactional
    public ResponseEntity<?> updatePriceMenuItem(CUPriceMenuItemReq cuPriceMenuItemReq) {
        LocalDate curDate = LocalDate.now();
        if(cuPriceMenuItemReq.getApplicationDate().isBefore(curDate)) {
            return ResponseEntity.badRequest().body("Application Date must be >= now");
        }
        MenuItem menuItem = menuItemRepository.findById(cuPriceMenuItemReq.getMenuItemId()).get();
        if (cuPriceMenuItemReq.getApplicationDate().isEqual(curDate)
                && tableHistoryMenuItemRepository.isHasTableOrderOnDate(menuItem.getMenuItemId(),curDate))
            return ResponseEntity.badRequest().body("Has table SERVING with this menu item, can't change price");
        PriceMenuItem priceMenuItem = priceMenuItemRepository.findById(cuPriceMenuItemReq.getPriceMenuItemId()).get();
        priceMenuItem.setApplicationDate(cuPriceMenuItemReq.getApplicationDate());
        priceMenuItem.setPrice(cuPriceMenuItemReq.getPrice());
        try {
            priceMenuItem = priceMenuItemRepository.save(priceMenuItem);
            return ResponseEntity.ok(modelMapper.map(priceMenuItem, PriceMenuItemView.class));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @Transactional
    public ResponseEntity<?> deletePriceMenuItem(Integer id) {
        PriceMenuItem priceMenuItem = priceMenuItemRepository.findById(id).get();
        if(tableHistoryMenuItemRepository.existsByPriceMenuItem_PriceMenuItemId(id))
            return ResponseEntity.badRequest().body("has order, can't delete");
        try {
            priceMenuItemRepository.delete(priceMenuItem);
            return ResponseEntity.ok("Successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    public ResponseEntity<?> getByMenuItem(Integer menuItemId) {
        List<PriceMenuItem> priceMenuItems = priceMenuItemRepository.findAllByMenuItem_MenuItemId(menuItemId);
        return ResponseEntity.ok(priceMenuItems.stream()
                .map(priceMenuItem -> modelMapper.map(priceMenuItem, PriceMenuItemView.class))
                .collect(Collectors.toList())
        );
    }

    public ResponseEntity<?> getCurrentPriceOfAllMenuItemInDate(LocalDate date){
        List<PriceMenuItem> priceMenuItems = priceMenuItemRepository.findCurrentPricesByMenuItemOnDate(date);
        return ResponseEntity.ok(priceMenuItems.stream()
                .map(priceMenuItem -> modelMapper.map(priceMenuItem, PriceMenuItemView.class))
                .collect(Collectors.toList())
        );
    }

    @Transactional
    public ResponseEntity<?> createPriceTicket(CUPriceTicketReq cuPriceTicketReq) {
        LocalDate curDate = LocalDate.now();
        if(cuPriceTicketReq.getApplicationDate().isBefore(curDate))
            return ResponseEntity.badRequest().body("Error, application date must be >= now");

        if(cuPriceTicketReq.getApplicationDate().isEqual(curDate)
            && tableHistoryRepository.existsByPrice_ApplicationDate(curDate))
            return ResponseEntity.badRequest().body("ticket has been set price in today, can't change price");
        DayGroup dayGroup =dayGroupRepository.findById(cuPriceTicketReq.getDayGroupId()).get();
        Price price = new Price(dayGroup,cuPriceTicketReq.getAdultPrice(),cuPriceTicketReq.getChildPrice());
        try {
            price = priceRepository.save(price);
            return ResponseEntity.ok(modelMapper.map(price, PriceView.class));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }

    @Transactional
    public ResponseEntity<?> updatePriceTicket(CUPriceTicketReq cuPriceTicketReq) {
        LocalDate curDate = LocalDate.now();
        if(cuPriceTicketReq.getApplicationDate().isBefore(curDate))
            return ResponseEntity.badRequest().body("Error, application date must be >= now");

        if(cuPriceTicketReq.getApplicationDate().isEqual(curDate)
                && tableHistoryRepository.existsByPrice_ApplicationDate(curDate))
            return ResponseEntity.badRequest().body("ticket has been set price in today, can't change price");
        DayGroup dayGroup =dayGroupRepository.findById(cuPriceTicketReq.getDayGroupId()).get();
        Price price = priceRepository.findById(cuPriceTicketReq.getPriceId()).get();
        price.setAdultPrice(cuPriceTicketReq.getAdultPrice());
        price.setChildPrice(cuPriceTicketReq.getChildPrice());
        price.setApplicationDate(cuPriceTicketReq.getApplicationDate());
        price.setDayGroup(dayGroup);
        try {
            priceRepository.save(price);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }

    @Transactional
    public ResponseEntity<?> deletePriceTicket(Integer id) {
        Price price = priceRepository.findById(id).get();
        if(tableHistoryRepository.existsByPrice_PriceId(id))
            return ResponseEntity.badRequest().body("has order, can't delete");
        try {
            priceRepository.delete(price);
            return ResponseEntity.ok("Successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    public ResponseEntity<?> getByDayGroup(Integer groupId) {
        return ResponseEntity.ok(priceRepository.findAllByDayGroup_DateGroupId(groupId)
                .stream().map(price -> modelMapper.map(price, PriceView.class))
                .collect(Collectors.toList())
        );
    }

    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(priceRepository.findAll()
                .stream().map(price -> modelMapper.map(price, PriceView.class))
                .collect(Collectors.toList())
        );
    }

    public ResponseEntity<?> getTicketByDate(LocalDate date) {
        return ResponseEntity.ok(priceRepository.findPricesByDate(date)
                .stream().map(price -> modelMapper.map(price, PriceView.class))
                .collect(Collectors.toList())
        );
    }

    public ResponseEntity<?> getTicketByDateRegis(LocalDate date) {
        Price price = getPriceTicketToDate(date);
        if(price == null)
            return ResponseEntity.badRequest().body("No price found for date regis");
        return ResponseEntity.ok(modelMapper.map(price,PriceView.class));
    }

    public Price getPriceTicketToDate(LocalDate date){
        Holiday holiday = holidayRepository.findByDateAndMonth(date.getDayOfMonth(),date.getMonthValue(),date.getYear());
        DayGroup dayGroup = null;
        if(holiday != null)
            dayGroup = dayGroupRepository.findByDayGroupNameAndTrue(DayGroupName.HOLIDAY);
        else if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
            dayGroup = dayGroupRepository.findByDayGroupNameAndTrue(DayGroupName.WEEKEND);
        else dayGroup = dayGroupRepository.findByDayGroupNameAndTrue(DayGroupName.WEEKDAY);
        Price price = priceRepository.findByDateGroupId(date,dayGroup.getDateGroupId());
        return price;
    }
}
