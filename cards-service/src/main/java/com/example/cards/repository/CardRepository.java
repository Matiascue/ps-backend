package com.example.cards.repository;

import com.example.cards.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardEntity,Long> {

    Optional<CardEntity> findByCode(String code);
}
