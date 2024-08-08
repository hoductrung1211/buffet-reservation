package com.example.demo.repository;

import com.example.demo.model.bill.TableHistoryMenuItem;
import com.example.demo.model.bill.TableHistoryMenuItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ITableHistoryMenuItemRepository extends JpaRepository<TableHistoryMenuItem, TableHistoryMenuItemId> {
    @Query("SELECT CASE WHEN COUNT(thm) > 0 THEN FALSE ELSE TRUE END " +
            "FROM TableHistoryMenuItem thm WHERE thm.priceMenuItem.menuItem.menuItemId = :menuItemId")
    boolean isHasTableOrder(@Param("menuItemId") Integer menuItemId);

    @Query("SELECT CASE WHEN COUNT(thm) > 0 THEN FALSE ELSE TRUE END " +
            "FROM TableHistoryMenuItem thm " +
            "WHERE thm.tableHistory.tableHistoryStatus = 'SERVING' " +
            "AND thm.priceMenuItem.menuItem.menuItemId = :menuItemId " +
            "AND FUNCTION('DATE', thm.createDateTime) = :date")
    boolean isHasTableOrderOnDate(@Param("menuItemId") Integer menuItemId,
                                  @Param("date") LocalDate date);

    boolean existsByPriceMenuItem_PriceMenuItemId(Integer id);

    @Query("SELECT thm FROM TableHistoryMenuItem thm WHERE thm.tableHistory.tableHistoryId = :tableHistoryId " +
            "AND thm.priceMenuItem.priceMenuItemId = :priceMenuItemId ")

    TableHistoryMenuItem findByHisAndPrice(@Param("tableHistoryId") Integer tableHistoryId,
                                           @Param("priceMenuItemId") Integer priceMenuItemId);

    List<TableHistoryMenuItem> findAllByTableHistory_TableHistoryId(Integer id);
}
