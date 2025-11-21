package com.example.cards.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;
    private int quantity;
}
