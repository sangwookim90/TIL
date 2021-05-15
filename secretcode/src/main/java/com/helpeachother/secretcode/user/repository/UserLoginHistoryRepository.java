package com.helpeachother.secretcode.user.repository;

import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.entity.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {

}
