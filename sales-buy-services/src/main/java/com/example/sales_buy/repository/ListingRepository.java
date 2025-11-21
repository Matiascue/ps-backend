package com.example.sales_buy.repository;

import com.example.sales_buy.entity.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<ListingEntity,Long> {
    List<ListingEntity>getAllByUserId(Long userId);
}
