package com.example.users.repository;

import com.example.users.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity,Long> {
    List<FriendEntity>findAllByUserIdAndStatus(Long userId,String status);
    List<FriendEntity>findAllByFriendIdAndStatus(Long friendId,String status);
    Optional<FriendEntity>findByUserIdAndFriendId(Long userId,Long friendId);
    Optional<FriendEntity>findByUserIdAndFriendIdAndStatus(Long userId,Long friendId,String status);
}
