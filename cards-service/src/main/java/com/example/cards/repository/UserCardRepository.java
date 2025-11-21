package com.example.cards.repository;

import com.example.cards.entity.UserCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCardRepository extends JpaRepository<UserCardEntity,Long> {
    Optional<UserCardEntity>findByCard_CodeAndUserId(String code,Long id);
    List<UserCardEntity>findAllByUserId(Long id);
    Optional<UserCardEntity>findByCard_IdAndAndUserId(Long cardId,Long userId);
}
