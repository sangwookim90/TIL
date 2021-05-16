package com.helpeachother.secretcode.user.service;

import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.model.UserLogCount;
import com.helpeachother.secretcode.user.model.UserNoticeCount;
import com.helpeachother.secretcode.user.model.UserStatus;
import com.helpeachother.secretcode.user.model.UserSummary;
import com.helpeachother.secretcode.user.repository.UserCustomRepository;
import com.helpeachother.secretcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;

    @Override
    public UserSummary getUserStatusCount() {
        long usingCount = userRepository.countByStatus(UserStatus.Using);
        long stopUserCount = userRepository.countByStatus(UserStatus.Stop);
        long totalUserCount = userRepository.count();

        return UserSummary.builder()
                .usingUserCount(usingCount)
                .stopUserCount(stopUserCount)
                .totalUserCount(totalUserCount)
                .build();

    }

    @Override
    public List<User> getTodayUsers() {

        LocalDateTime t = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(t.getYear(), t.getMonth(), t.getDayOfMonth(), 0, 0);
        LocalDateTime endDate = startDate.plusDays(1);

        List<User> userList = userRepository.findToday(startDate, endDate);

        return userList;
    }

    @Override
    public List<UserNoticeCount> getUserNoticeCount() {
        return userCustomRepository.findUserNoticeCount();
    }

    @Override
    public List<UserLogCount> getUserLogCount() {
        return userCustomRepository.findUserLogCount();
    }

    @Override
    public List<UserLogCount> getUserLikeBest() {
        return userCustomRepository.findUserLikeBest();
    }
}
