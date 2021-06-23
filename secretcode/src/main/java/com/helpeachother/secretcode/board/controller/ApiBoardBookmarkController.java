package com.helpeachother.secretcode.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.helpeachother.secretcode.board.service.BoardService;
import com.helpeachother.secretcode.common.model.ResponseResult;
import com.helpeachother.secretcode.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiBoardBookmarkController {

    private final BoardService boardService;

    @PutMapping("/api/board/{id}/bookmark")
    public ResponseEntity<?> boardBookmark(@PathVariable long id,
                                        @RequestHeader String token) {
        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        return ResponseResult.success(boardService.addBookmark(id, email));
    }

    @DeleteMapping("/api/bookmark/{id}")
    public ResponseEntity<?> deleteBoardBookmark(@PathVariable long id,
                                        @RequestHeader String token) {

        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        return ResponseResult.success(boardService.removeBookmark(id, email));
    }
}
