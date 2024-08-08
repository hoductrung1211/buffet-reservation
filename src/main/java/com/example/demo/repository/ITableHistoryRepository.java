package com.example.demo.repository;

import com.example.demo.model.bill.TableHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ITableHistoryRepository extends JpaRepository<TableHistory, Integer> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN FALSE ELSE TRUE END " +
            "FROM TableHistory t WHERE t.reservation.date = :date " +
            "AND t.reservation.reservationTimeFrame.reservationTimeFrameId = :frameId " +
            "AND t.buffetTable.buffetTableId = :buffetId")
    boolean isHasSlotOnline(@Param("date") LocalDate date,
                      @Param("frameId") Integer frameId,
                      @Param("buffetId") Integer buffetId);
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN FALSE ELSE TRUE END " +
            "FROM TableHistory t " +
            "WHERE ((t.reservation.date = :date " +
            "AND t.reservation.reservationTimeFrame.startTime <= :curTime " +
            "AND t.reservation.reservationTimeFrame.endTime >= :curTime) " +
            "OR (FUNCTION('DATE',t.startDateTime) = :date AND t.startDateTime <= :curDateTime)) " +
            "AND t.buffetTable.buffetTableId = :buffetId")
    boolean isHasSlotOnOff(@Param("date") LocalDate date,
                       @Param("curTime") LocalTime curTime,
                       @Param("curDateTime") LocalDateTime curDateTime,
                       @Param("buffetId") Integer buffetId);


    TableHistory findByReservation_ReservationId(Integer id);

    boolean existsByPrice_ApplicationDate(LocalDate date);
    boolean existsByPrice_PriceId(Integer date);
}
