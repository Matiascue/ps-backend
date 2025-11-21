package com.example.users.repository;

import com.example.users.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolEntity,Long> {
    Optional<RolEntity>findByDescription(String description);
}
