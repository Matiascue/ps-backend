package com.example.users.repository;

import com.example.users.dto.dashboard.UserScoreDto;
import com.example.users.entity.ScoreEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity,Long> {
    List<ScoreEntity>findAllByUserRatedId(Long userRatedId);
    @Query("SELECT COALESCE(AVG(s.score),0) FROM ScoreEntity s WHERE s.userRated.id=:userRatedId")
    double findAverageScoredByUserRatedId(@Param("userRatedId")Long userRatedId);
    @Query("""
    SELECT new com.example.users.dto.dashboard.UserScoreDto(
        s.userRated.username, AVG(s.score), COUNT(s.id)
    )
    FROM ScoreEntity s
    WHERE (:active IS NULL OR s.userRated.active = :active)
      AND (:role IS NULL OR s.userRated.role.description = :role)
      AND (:fromDate IS NULL OR s.date >= :fromDate)
      AND (:toDate IS NULL OR s.date <= :toDate)
    GROUP BY s.userRated.id
    ORDER BY AVG(s.score) DESC
    """)
    List<UserScoreDto> findTopRatedUsers(@Param("active") Boolean active,
                                         @Param("role") String role,
                                         @Param("fromDate") LocalDateTime fromDate,
                                         @Param("toDate") LocalDateTime toDate,Pageable pageable);
    @Query("""
SELECT new com.example.users.dto.dashboard.UserScoreDto(
        s.userRated.username, AVG(s.score), COUNT(s.id)
    ) FROM ScoreEntity s
WHERE (:active IS NULL OR s.userRated.active = :active)
      AND (:role IS NULL OR s.userRated.role.description = :role)
      AND (:fromDate IS NULL OR s.date >= :fromDate)
      AND (:toDate IS NULL OR s.date <= :toDate)
    GROUP BY s.userRated.id
    ORDER BY AVG(s.score) ASC
""")
    List<UserScoreDto> findWorstRatedUsers(@Param("active") Boolean active,
                                         @Param("role") String role,
                                         @Param("fromDate") LocalDateTime fromDate,
                                         @Param("toDate") LocalDateTime toDate,Pageable pageable);
}
