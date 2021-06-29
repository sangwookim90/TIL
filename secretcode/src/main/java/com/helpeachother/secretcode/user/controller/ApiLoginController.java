package com.helpeachother.secretcode.user.controller;

import com.helpeachother.secretcode.common.exception.BizException;
import com.helpeachother.secretcode.common.model.ResponseResult;
import com.helpeachother.secretcode.notice.model.ResponseError;
import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.model.UserLogin;
import com.helpeachother.secretcode.user.model.UserLoginToken;
import com.helpeachother.secretcode.user.service.UserService;
import com.helpeachother.secretcode.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiLoginController {

    private final UserService userService;

    /**
     * 회원 로그인 히스토리 기능 API + 로그인시 에러가 발생한 경우 로그에 기록하는 기능
     */
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLogin userLogin, Errors errors) {

        if(errors.hasErrors()) {
            return ResponseResult.fail("입력값이 정확하지 않습니다.", ResponseError.of(errors.getAllErrors()));
        }

        User user = null;
        try {
            user = userService.login(userLogin);
        } catch (BizException e) {
            log.info("로그인 에러:" + e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }

        UserLoginToken userLoginToken = JwtUtils.createToken(user);
        if(userLoginToken == null) {
            log.info("JWT 생성 에러");
            return ResponseResult.fail("JWT 생성에 실패하였습니다.");
        }
        return ResponseResult.success(userLoginToken);
    }

}
