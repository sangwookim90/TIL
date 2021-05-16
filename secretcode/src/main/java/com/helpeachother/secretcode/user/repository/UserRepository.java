package com.helpeachother.secretcode.user.repository;

import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    int countByEmail(String email);

    Optional<User> findByIdAndPassword(long id, String password);

    Optional<User> findByUserNameAndPhone(String userName, String phone);

    Optional<User> findByEmail(String email);

    List<User> findByEmailNotContainsOrPhoneContainsOrUserNameContains(String email, String phone, String userName);

    long countByStatus(UserStatus userStatus);

    @Query("select u from User u where u.regDate between :startDate and :endDate ")
    List<User> findToday(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
