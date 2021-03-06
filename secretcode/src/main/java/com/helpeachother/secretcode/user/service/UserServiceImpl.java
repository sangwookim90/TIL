package com.helpeachother.secretcode.user.service;

import com.helpeachother.secretcode.common.exception.BizException;
import com.helpeachother.secretcode.common.model.ServiceResult;
import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.entity.UserInterest;
import com.helpeachother.secretcode.user.model.*;
import com.helpeachother.secretcode.user.repository.UserCustomRepository;
import com.helpeachother.secretcode.user.repository.UserInterestRepository;
import com.helpeachother.secretcode.user.repository.UserRepository;
import com.helpeachother.secretcode.util.PasswordUtils;
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
            throw new BizException("?????? ????????? ???????????? ????????????.");
        }
        User user = optionalUser.get();

        if(!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
            throw new BizException("???????????? ????????? ????????????.");
        }

        return user;
    }

    @Override
    public ServiceResult addInterestUser(String email, Long id) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("?????? ????????? ???????????? ????????????.");
        }
        User user = optionalUser.get();

        Optional<User> optionalInterestUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("?????????????????? ????????? ?????? ????????? ???????????? ????????????.");
        }
        User interestUser = optionalInterestUser.get();

        // ????????? ???????????? ??????
        if(user.getId() == interestUser.getId()) {
            return ServiceResult.fail("????????? ?????????????????? ????????? ??? ????????????.");
        }

        if(userInterestRepository.countByUserAndInterestUser(user,interestUser) > 0) {
            return ServiceResult.fail("?????? ??????????????? ????????? ?????????????????????.");
        }
        UserInterest userInterest = UserInterest.builder()
                .user(user)
                .interestUser(interestUser)
                .regDate(LocalDateTime.now())
                .build();

        userInterestRepository.save(userInterest);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult removeInterestUser(String email, Long interestId) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("?????? ????????? ???????????? ????????????.");
        }
        User user = optionalUser.get();

        Optional<UserInterest> optionalUserInterest = userInterestRepository.findById(interestId);

        if(!optionalUserInterest.isPresent()) {
            return ServiceResult.fail("????????? ????????? ????????????.");
        }

        UserInterest userInterest = optionalUserInterest.get();

        if(userInterest.getUser().getId() != user.getId()) {
            return ServiceResult.fail("????????? ????????? ????????? ????????? ??? ????????????.");
        }

        userInterestRepository.delete(userInterest);

        return ServiceResult.success();
    }
}
