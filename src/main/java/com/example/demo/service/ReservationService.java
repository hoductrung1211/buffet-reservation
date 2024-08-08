package com.example.demo.service;

import com.example.demo.dto.price.PriceView;
import com.example.demo.dto.reservation.CUReservationReq;
import com.example.demo.dto.reservation.ReservationView;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.bill.TableHistory;
import com.example.demo.model.bill.TableHistoryStatus;
import com.example.demo.model.price.Price;
import com.example.demo.model.reservation.Reservation;
import com.example.demo.model.reservation.ReservationStatus;
import com.example.demo.model.reservation.ReservationTimeFrame;
import com.example.demo.model.table.BuffetTable;
import com.example.demo.model.table.TableGroup;
import com.example.demo.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final IReservationRepository reservationRepository;
    private final IReservationTimeFrameRepository reservationTimeFrameRepository;
    private final ITableHistoryRepository tableHistoryRepository;
    private final IBuffetTableRepository buffetTableRepository;
    private final ContextHolderService contextHolderService;
    private final ModelMapper modelMapper;
    private final PriceService priceService;
    private final ICustomerRepository customerRepository;

    @Autowired
    public ReservationService(IReservationRepository reservationRepository, IReservationTimeFrameRepository reservationTimeFrameRepository, ITableHistoryRepository tableHistoryRepository, IBuffetTableRepository buffetTableRepository, ContextHolderService contextHolderService, ModelMapper modelMapper, PriceService priceService, ICustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeFrameRepository = reservationTimeFrameRepository;
        this.tableHistoryRepository = tableHistoryRepository;
        this.buffetTableRepository = buffetTableRepository;
        this.contextHolderService = contextHolderService;
        this.modelMapper = modelMapper;
        this.priceService = priceService;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public ResponseEntity<?> createReservation(CUReservationReq cuReservationReq) {
        // check time
        LocalDate curDate = LocalDate.now();
        if(cuReservationReq.getDateRegis().isBefore(curDate))
            return ResponseEntity.badRequest().body("date register must have >= current date");

        ReservationTimeFrame reservationTimeFrame = reservationTimeFrameRepository.findById(cuReservationReq.getReservationTimeFrameId()).get();
        LocalTime time = LocalTime.now();
        if(cuReservationReq.getDateRegis().isEqual(curDate)
                && time.isAfter(reservationTimeFrame.getStartTime()))
            return ResponseEntity.badRequest().body("Invalid registration time frame: "+time+"-x-> ["
                    +reservationTimeFrame.getStartTime()+"--"+reservationTimeFrame.getEndTime()+"]");
        if(cuReservationReq.getAdultsQuantity() < 0 || cuReservationReq.getAdultsQuantity() > 20
            || cuReservationReq.getChildrenQuantity() < 0 || cuReservationReq.getChildrenQuantity() > 20
            || (cuReservationReq.getAdultsQuantity() == 0 && cuReservationReq.getChildrenQuantity() == 0))
            return ResponseEntity.badRequest().body("Adult or Children must be to in rate [0-20] and total quantity of them > 0");

        // check slot
        List<BuffetTable> buffetTables = buffetTableRepository.findAllByQuantity(cuReservationReq.getAdultsQuantity()+cuReservationReq.getChildrenQuantity());
        TableHistory tableHistory = new TableHistory();
        for (BuffetTable buffetTable : buffetTables){
            if(tableHistoryRepository.isHasSlotOnline(cuReservationReq.getDateRegis(),cuReservationReq.getReservationTimeFrameId(),buffetTable.getBuffetTableId())){
                tableHistory.setBuffetTable(buffetTable);
                break;
            }
        }
        if(tableHistory.getBuffetTable() == null) {
            return ResponseEntity.badRequest().body("No table found for the required number of people");
        }
        // price add (not yet)
        Price price = priceService.getPriceTicketToDate(curDate);
        if(price == null)
            return ResponseEntity.badRequest().body("No price found for date regis");
        tableHistory.setPrice(price);
        Customer customer = (Customer) contextHolderService.getObjectFromContext();
        Reservation reservation = new Reservation(customer,reservationTimeFrame,cuReservationReq.getDateRegis(),
                cuReservationReq.getAdultsQuantity(),cuReservationReq.getChildrenQuantity(),
                cuReservationReq.getNote(), ReservationStatus.INIT);
        tableHistory.setReservation(reservation);
        tableHistory.setTableHistoryStatus(TableHistoryStatus.RESERVED);

        try {
            reservation = reservationRepository.save(reservation);
            reservationRepository.flush();
            tableHistoryRepository.save(tableHistory);
            ReservationView reservationView = modelMapper.map(reservation,ReservationView.class);
            reservationView.setPrice(modelMapper.map(reservation.getTableHistory().getPrice(), PriceView.class));
            reservationView.setTableName(tableHistory.getBuffetTable().getBuffetTableName());
            return ResponseEntity.ok(reservationView);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Reservation is failed!");
        }
    }

    @Transactional
    public ResponseEntity<?> updateReservation(CUReservationReq cuReservationReq) {
        Reservation reservation = reservationRepository.findById(cuReservationReq.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found reservation"));
        // check time
        LocalDate curDate = LocalDate.now();
        if((reservation.getDate() != cuReservationReq.getDateRegis())
        && cuReservationReq.getDateRegis().isBefore(curDate))
            return ResponseEntity.badRequest().body("date register must have >= current date");
        reservation.setDate(cuReservationReq.getDateRegis());

        if(reservation.getReservationTimeFrame().getReservationTimeFrameId() != cuReservationReq.getReservationTimeFrameId()){
            ReservationTimeFrame reservationTimeFrame = reservationTimeFrameRepository.findById(cuReservationReq.getReservationTimeFrameId()).get();
            LocalTime time = LocalTime.now();
            if(cuReservationReq.getDateRegis().isEqual(curDate)
                && time.isAfter(reservationTimeFrame.getStartTime()))
                return ResponseEntity.badRequest().body("Invalid registration time frame: "+time+"-x-> ["
                        +reservationTimeFrame.getStartTime()+"--"+reservationTimeFrame.getEndTime()+"]");
            if(cuReservationReq.getAdultsQuantity() < 0 || cuReservationReq.getAdultsQuantity() > 20
                    || cuReservationReq.getChildrenQuantity() < 0 || cuReservationReq.getChildrenQuantity() > 20
                    || (cuReservationReq.getAdultsQuantity() == 0 && cuReservationReq.getChildrenQuantity() == 0))
                return ResponseEntity.badRequest().body("Adult or Children must be to in rate [0-20] and total quantity of them > 0");

            reservation.setReservationTimeFrame(reservationTimeFrame);
        }
        // check slot
        TableHistory tableHistory = tableHistoryRepository.findByReservation_ReservationId(reservation.getReservationId());
        boolean isOldTotalQuantity = (cuReservationReq.getAdultsQuantity()+cuReservationReq.getChildrenQuantity()) == (reservation.getChildrenQuantity()+reservation.getAdultsQuantity());
        if(!cuReservationReq.getDateRegis().isEqual(reservation.getDate()) || !cuReservationReq.getReservationTimeFrameId().equals(reservation.getReservationTimeFrame().getReservationTimeFrameId())
        || !isOldTotalQuantity){
            List<BuffetTable> buffetTables = buffetTableRepository.findAllByQuantity(cuReservationReq.getAdultsQuantity()+cuReservationReq.getChildrenQuantity());
            for (BuffetTable buffetTable : buffetTables){
                if(tableHistoryRepository.isHasSlotOnline(cuReservationReq.getDateRegis(),cuReservationReq.getReservationTimeFrameId(),buffetTable.getBuffetTableId())){
                    tableHistory.setBuffetTable(buffetTable);
                    break;
                }
            }
            if(tableHistory.getBuffetTable() == null)
                return ResponseEntity.badRequest().body("No table found for the required number of people");

            Price price = priceService.getPriceTicketToDate(curDate);
            if(price == null)
                return ResponseEntity.badRequest().body("No price found for date regis");
            tableHistory.setPrice(price);
        }

        Customer customer = (Customer) contextHolderService.getObjectFromContext();
        if(reservation.getCustomer().getCustomerId() != customer.getCustomerId())
            return ResponseEntity.badRequest().body("error customer");
        ReservationStatus.valueOf(cuReservationReq.getStatus());
        reservation.setStatus(ReservationStatus.valueOf(cuReservationReq.getStatus()));
        try {
            reservation = reservationRepository.save(reservation);
            reservationRepository.flush();
            tableHistoryRepository.save(tableHistory);
            ReservationView reservationView = modelMapper.map(reservation,ReservationView.class);
            reservationView.setTableName(tableHistory.getBuffetTable().getBuffetTableName());
            reservationView.setPrice(modelMapper.map(reservation.getTableHistory().getPrice(), PriceView.class));
            return ResponseEntity.ok(reservationView);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Reservation is failed!");
        }
    }

    @Transactional
    public ResponseEntity<?> cancelReservation(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found reservation"));

        LocalTime time = LocalTime.now();
        // date >= now && time
        Object object = contextHolderService.getObjectFromContext();
        if(object instanceof Customer){
            if(reservation.getDate().isAfter(LocalDate.now()))
                return ResponseEntity.badRequest().body("Invalid time: now >= date register");
            if((reservation.getDate().isEqual(LocalDate.now()) && time.isAfter(reservation.getReservationTimeFrame().getStartTime())))
                return ResponseEntity.badRequest().body("Invalid time: time now >= time frame");
        }
        reservation.setStatus(ReservationStatus.CANCELED);
        TableHistory tableHistory = tableHistoryRepository.findByReservation_ReservationId(reservationId);
        tableHistory.setTableHistoryStatus(TableHistoryStatus.CANCELED);
        try {
            reservationRepository.save(reservation);
            tableHistoryRepository.save(tableHistory);
            return ResponseEntity.badRequest().body("Cancel is successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("cancel is failed");
        }
    }


    public ResponseEntity<?> getReservation() {
        Customer customer = (Customer) contextHolderService.getObjectFromContext();
        List<ReservationView> reservationViews = reservationRepository.findAllByCustomer_CustomerId(customer.getCustomerId())
                .stream().map(reservation -> {
                    ReservationView reservationView = modelMapper.map(reservation,ReservationView.class);
                    reservationView.setTableName(reservation.getTableHistory().getBuffetTable().getBuffetTableName());
                    reservationView.setPrice(modelMapper.map(reservation.getTableHistory().getPrice(), PriceView.class));
                    return reservationView;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationViews);
    }

    public ResponseEntity<?> getReservationByDate(LocalDate date) {
        Customer customer = (Customer) contextHolderService.getObjectFromContext();
        List<ReservationView> reservationViews;
        if(date == null){
            reservationViews = reservationRepository.findAllByCustomer_CustomerId(customer.getCustomerId())
                    .stream().map(reservation -> {
                        ReservationView reservationView = modelMapper.map(reservation,ReservationView.class);
                        reservationView.setTableName(reservation.getTableHistory().getBuffetTable().getBuffetTableName());
                        reservationView.setPrice(modelMapper.map(reservation.getTableHistory().getPrice(), PriceView.class));
                        return reservationView;
                    })
                    .collect(Collectors.toList());
        }else {
            reservationViews = reservationRepository.findAllByCustomer_CustomerIdAndDate(customer.getCustomerId(),date)
                    .stream().map(reservation -> {
                        ReservationView reservationView = modelMapper.map(reservation,ReservationView.class);
                        reservationView.setTableName(reservation.getTableHistory().getBuffetTable().getBuffetTableName());
                        reservationView.setPrice(modelMapper.map(reservation.getTableHistory().getPrice(), PriceView.class));
                        return reservationView;
                    })
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(reservationViews);
    }

    public ResponseEntity<?> getReservationDetail(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        ReservationView reservationView = modelMapper.map(reservation,ReservationView.class);
        reservationView.setTableName(reservation.getTableHistory().getBuffetTable().getBuffetTableName());
        reservationView.setPrice(modelMapper.map(reservation.getTableHistory().getPrice(), PriceView.class));
        return ResponseEntity.ok(reservationView);
    }

    public ResponseEntity<?> getReservationCheckIn(String phone, LocalDate date) {
        List<Reservation> reservationList;
        List<ReservationView> reservationViews;
        if(phone.isBlank()) {
            // all
            reservationList = reservationRepository.findAllByDate(date);
            reservationViews = reservationList.stream().map(reservation -> {
                        ReservationView reservationView = modelMapper.map(reservation,ReservationView.class);
                        reservationView.setTableName(reservation.getTableHistory().getBuffetTable().getBuffetTableName());
                        reservationView.setPrice(modelMapper.map(reservation.getTableHistory().getPrice(), PriceView.class));
                        return reservationView;
                    })
                    .collect(Collectors.toList());
        }else {
            Customer customer = customerRepository.findCustomerByPhone(phone);
            reservationList = reservationRepository.findAllByCustomer_CustomerIdAndDate(customer.getCustomerId(),date);
            reservationViews = reservationList.stream().map(reservation -> {
                        ReservationView reservationView = modelMapper.map(reservation,ReservationView.class);
                        reservationView.setTableName(reservation.getTableHistory().getBuffetTable().getBuffetTableName());
                        reservationView.setPrice(modelMapper.map(reservation.getTableHistory().getPrice(), PriceView.class));
                        return reservationView;
                    })
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(reservationViews);
    }
}
