package com.example.sales_buy.repository;

import com.example.sales_buy.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,Long> {
    List<SaleEntity>findAllByBuyerId(Long userId);
    @Query("SELECT s.buyerId, COUNT(s),SUM(s.totalAmount) " +
            "FROM SaleEntity s " +
            "WHERE s.saleDate BETWEEN :start AND :end " +
            "GROUP BY s.buyerId ORDER BY COUNT(s) DESC")
    List<Object[]>findTopBuyerIdsWithCounts(LocalDate start, LocalDate end);
    @Query("""
   SELECT s.buyerId, COUNT(s), SUM(s.totalAmount)
   FROM SaleEntity s
   WHERE s.saleDate BETWEEN :start AND :end AND s.buyerId = :userId
GROUP BY s.buyerId
""")
    List<Object[]> findBuyerTotalsByUserId(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("userId") Long userId);

}
