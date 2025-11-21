package com.example.users.repository;

import com.example.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity>findByEmail(String email);
    Optional<UserEntity>findByUsername(String username);
    long countByActive(boolean activate);
}
