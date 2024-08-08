package com.example.demo.repository;

import com.example.demo.model.menu.PriceMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPriceMenuItemRepository extends JpaRepository<PriceMenuItem,Integer> {
    List<PriceMenuItem> findAllByMenuItem_MenuItemId(Integer id);

    boolean existsByApplicationDateAndMenuItem_MenuItemId(LocalDate date,Integer menuId);

    @Query("SELECT p FROM PriceMenuItem p WHERE p.applicationDate = " +
            "(SELECT MAX(p2.applicationDate) " +
            "FROM PriceMenuItem p2 " +
            "WHERE p2.menuItem.menuItemId = p.menuItem.menuItemId " +
            "AND p2.applicationDate <= :date )")
    List<PriceMenuItem> findCurrentPricesByMenuItemOnDate(@Param("date") LocalDate date);

    @Query("SELECT p FROM PriceMenuItem p " +
            "WHERE p.menuItem.menuItemId = :menuItemId " +
            "AND p.applicationDate = (" +
            "  SELECT MAX(p2.applicationDate) " +
            "  FROM PriceMenuItem p2 " +
            "  WHERE p2.menuItem.menuItemId = :menuItemId " +
            "  AND p2.applicationDate <= :date" +
            ")")
    PriceMenuItem findCurPriceByMenuItemOnDate(@Param("date") LocalDate date,@Param("menuItemId") Integer id);



}
