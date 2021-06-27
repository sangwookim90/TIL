package com.helpeachother.secretcode.user.controller;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.helpeachother.secretcode.common.model.ResponseResult;
import com.helpeachother.secretcode.common.model.ServiceResult;
import com.helpeachother.secretcode.user.service.UserService;
import com.helpeachother.secretcode.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiUserInterestController {


    private final UserService userService;

    /**
     *  관심사용자에 등록하는 API
     */

    @PutMapping("/api/user/{id}/interest")
    public ResponseEntity<?> interestUser(@PathVariable Long id,
                             @RequestHeader("F-TOKEN") String token) {

        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = userService.addInterestUser(email, id);
        return ResponseResult.result(result);
    }

    @DeleteMapping("/api/user/interest/{id}")
    public ResponseEntity<?> deleteInterestUser(@PathVariable Long id,
                                                @RequestHeader("F-TOKEN") String token) {

        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = userService.removeInterestUser(email, id);
        return ResponseResult.result(result);
    }


}
