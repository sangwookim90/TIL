package com.helpeachother.secretcode.user.service;

import com.helpeachother.secretcode.common.model.ServiceResult;
import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.entity.UserInterest;
import com.helpeachother.secretcode.user.model.*;
import com.helpeachother.secretcode.user.repository.UserCustomRepository;
import com.helpeachother.secretcode.user.repository.UserInterestRepository;
import com.helpeachother.secretcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final UserInterestRepository userInterestRepository;

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

    @Override
    public User login(UserLogin userLogin) {
        Optional<User> optionalUser = userRepository.findByEmail(userLogin.getEmail());
        if(!optionalUser.isPresent()) {
//            throw new BizException
        }
        User user = optionalUser.get();

        return user;

    }

    @Override
    public ServiceResult addInterestUser(String email, Long id) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        Optional<User> optionalInterestUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("관심사용자에 추가할 회원 정보가 존재하지 않습니다.");
        }
        User interestUser = optionalInterestUser.get();

        // 본인을 추가하는 경우
        if(user.getId() == interestUser.getId()) {
            return ServiceResult.fail("본인을 관심사용자로 추가할 수 없습니다.");
        }

        if(userInterestRepository.countByUserAndInterestUser(user,interestUser) > 0) {
            return ServiceResult.fail("이미 관심사용자 목록에 추가되었습니다.");
        }
        UserInterest userInterest = UserInterest.builder()
                .user(user)
                .interestUser(interestUser)
                .regDate(LocalDateTime.now())
                .build();

        userInterestRepository.save(userInterest);

        return ServiceResult.success();
    }
}
