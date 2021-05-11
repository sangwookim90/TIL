package com.helpeachother.secretcode.user.repository;

import com.helpeachother.secretcode.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    int countByEmail(String email);

    Optional<User> findByIdAndPassword(long id, String password);

    Optional<User> findByUserNameAndPhone(String userName, String phone);

    Optional<User> findByEmail(String email);
}
