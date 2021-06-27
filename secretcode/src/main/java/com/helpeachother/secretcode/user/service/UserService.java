package com.helpeachother.secretcode.user.service;

import com.helpeachother.secretcode.common.model.ServiceResult;
import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.model.UserLogCount;
import com.helpeachother.secretcode.user.model.UserLogin;
import com.helpeachother.secretcode.user.model.UserNoticeCount;
import com.helpeachother.secretcode.user.model.UserSummary;

import java.util.List;

public interface UserService {

    UserSummary getUserStatusCount();

    List<User> getTodayUsers();

    List<UserNoticeCount> getUserNoticeCount();

    List<UserLogCount> getUserLogCount();

    List<UserLogCount> getUserLikeBest();

    User login(UserLogin userLogin);

    /**
     * 관심 사용자 등록
     *
     * @param email
     * @param id
     * @return
     */
    ServiceResult addInterestUser(String email, Long id);

    /**
     * 관심 사용자 삭제
     *
     * @param email
     * @param id
     * @return
     */
    ServiceResult removeInterestUser(String email, Long id);
}