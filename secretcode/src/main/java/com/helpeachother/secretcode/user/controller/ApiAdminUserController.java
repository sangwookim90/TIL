package com.helpeachother.secretcode.user.controller;

import com.helpeachother.secretcode.notice.repository.NoticeRepository;
import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.entity.UserLoginHistory;
import com.helpeachother.secretcode.user.model.*;
import com.helpeachother.secretcode.user.repository.UserLoginHistoryRepository;
import com.helpeachother.secretcode.user.repository.UserRepository;
import com.helpeachother.secretcode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ApiAdminUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    private final UserService userService;

    @GetMapping("/api/admin/user")
    public ResponseMessage userList() {
        List<User> userList = userRepository.findAll();

        return ResponseMessage.success(userList);

    }

    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<?> userDetail(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent() == false) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(ResponseMessage.success(user));
    }

    @GetMapping("/api/admin/user/search")
    public ResponseEntity<?> findUser(@RequestBody UserSearch userSearch){
        // email like '%'

        List<User> userList = userRepository.findByEmailNotContainsOrPhoneContainsOrUserNameContains(
                userSearch.getEmail(),
                userSearch.getUserName(),
                userSearch.getPhone()
        );

        return ResponseEntity.ok().body(ResponseMessage.success(userList));
    }

    @PatchMapping("/api/admin/user/{id}/status")
    public ResponseEntity<?> userStatus(@PathVariable Long id, @RequestBody UserStatusInput userStatusInput) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent() == false) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        user.setStatus(userStatusInput.getStatus());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent() == false) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();

        if(noticeRepository.countByUser(user) > 0) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 작성한 공지사항이 있습니다."), HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/admin/user/login/history")
    public ResponseEntity<?> userLoginHistory() {
        List<UserLoginHistory> userLoginHistories = userLoginHistoryRepository.findAll();

        return ResponseEntity.ok().body(userLoginHistories);

    }

    @PatchMapping("/api/admin/user/{id}/lock")
    public ResponseEntity<?> userLock(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent() == false) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();
        if(user.isLockYn()) {
            return new ResponseEntity<>(ResponseMessage.fail("이미 접속제한이된 사용자 입니다."), HttpStatus.BAD_REQUEST);
        }

        user.setLockYn(true);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/api/admin/user/{id}/unlock")
    public ResponseEntity<?> userUnLock(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent() == false) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();
        if(user.isLockYn() == false) {
            return new ResponseEntity<>(ResponseMessage.fail("이미 접속제한이 해제된 사용자 입니다."), HttpStatus.BAD_REQUEST);
        }

        user.setLockYn(false);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/admin/user/status/count")
    public ResponseEntity<?> userStatusCount() {
        UserSummary userSummary = userService.getUserStatusCount();
        return ResponseEntity.ok().body(userSummary);
    }

    @GetMapping("/api/admin/user/today")
    public ResponseEntity<?> todayUser() {
        List<User> users = userService.getTodayUsers();

        return ResponseEntity.ok().body(ResponseMessage.success(users));
    }

    @GetMapping("/api/admin/user/notice/count")
    public ResponseEntity<?> userNoticeCount() {
        List<UserNoticeCount> userNoticeCountList = userService.getUserNoticeCount();
        return ResponseEntity.ok().body(ResponseMessage.success(userNoticeCountList));
    }

    @GetMapping("/api/admin/user/log/count")
    public ResponseEntity<?> userLogCount() {
        List<UserLogCount> userLogCounts = userService.getUserLogCount();
        return ResponseEntity.ok().body(ResponseMessage.success(userLogCounts));

    }


    // 좋아요를 가장 많이 한 사용자 목록 리턴
    @GetMapping("/api/admin/user/like/best")
    public ResponseEntity<?> bestLikeCount() {
        List<UserLogCount> userLogCounts = userService.getUserLikeBest();
        return ResponseEntity.ok().body(ResponseMessage.success(userLogCounts));
    }

}
