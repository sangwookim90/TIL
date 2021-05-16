package com.helpeachother.secretcode.user.service;

import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.model.UserLogCount;
import com.helpeachother.secretcode.user.model.UserNoticeCount;
import com.helpeachother.secretcode.user.model.UserSummary;

import java.util.List;

public interface UserService {

    UserSummary getUserStatusCount();

    List<User> getTodayUsers();

    List<UserNoticeCount> getUserNoticeCount();

    List<UserLogCount> getUserLogCount();

    List<UserLogCount> getUserLikeBest();
}