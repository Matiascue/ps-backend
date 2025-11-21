package com.example.sales_buy.repository;

import com.example.sales_buy.entity.SaleDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetailEntity,Long> {
    List<SaleDetailEntity>findAllBySaleId(Long saleId);
    List<SaleDetailEntity>findAllByListingId(Long listingId);
    @Query("""
    SELECT sd.fromUserId, SUM(sd.subtotal), SUM(sd.quantity)
    FROM SaleDetailEntity sd
    JOIN sd.sale s
    WHERE s.saleDate BETWEEN :from AND :to
    GROUP BY sd.fromUserId
    ORDER BY SUM(sd.subtotal) DESC
    """)
    List<Object[]>findTopSellerIdsWithTotals(@Param("from") LocalDate from, @Param("to") LocalDate to);
    @Query("""
SELECT sd.fromUserId, SUM(sd.subtotal), SUM(sd.quantity)
FROM SaleDetailEntity sd
JOIN sd.sale s
WHERE s.saleDate BETWEEN :from AND :to AND sd.fromUserId = :userId
GROUP BY sd.fromUserId
""")
    List<Object[]> findSellerTotalsByUserId(@Param("from") LocalDate from, @Param("to") LocalDate to, @Param("userId") Long userId);

}
