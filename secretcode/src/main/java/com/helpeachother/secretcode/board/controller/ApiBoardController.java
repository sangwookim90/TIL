package com.helpeachother.secretcode.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.helpeachother.secretcode.board.entity.Board;
import com.helpeachother.secretcode.board.entity.BoardType;
import com.helpeachother.secretcode.board.model.*;
import com.helpeachother.secretcode.board.service.BoardService;
import com.helpeachother.secretcode.common.exception.BizException;
import com.helpeachother.secretcode.common.model.ResponseResult;
import com.helpeachother.secretcode.common.model.ServiceResult;
import com.helpeachother.secretcode.notice.model.ResponseError;
import com.helpeachother.secretcode.user.model.ResponseMessage;
import com.helpeachother.secretcode.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {

    private final BoardService boardService;

    @PostMapping("/api/board/type")
    public ResponseEntity<?> addBoardType(@RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {
        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
            return new ResponseEntity<>(ResponseMessage.fail("입력값이 정확하지 않습니다", responseErrors), HttpStatus.BAD_REQUEST);
        }

        ServiceResult result = boardService.addBoard(boardTypeInput);
        if (result.isResult() == false) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @PutMapping("/api/board/type/{id}")
    public ResponseEntity<?> updateBoardType(@PathVariable Long id, @RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {
        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
            return new ResponseEntity<>(ResponseMessage.fail("입력값이 정확하지 않습니다", responseErrors), HttpStatus.BAD_REQUEST);
        }

        ServiceResult result =boardService.updateBoardType(id, boardTypeInput);
        if (result.isResult() == false) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @DeleteMapping("/api/board/type/{id}")
    public ResponseEntity<?> deleteBoardType(@PathVariable Long id) {
        ServiceResult result = boardService.deleteBoard(id);

        if (result.isResult() == false) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/board/type")
    public ResponseEntity<?> boardType() {
        List<BoardType> boardTypes = boardService.getAllBoardType();
        return ResponseEntity.ok().body(ResponseMessage.success(boardTypes));
    }

    @PatchMapping("/api/board/type/{id}/using")
    public ResponseEntity<?> usingBoardType(@PathVariable Long id, @RequestBody BoardTypeUsing boardTypeUsing){
        ServiceResult result = boardService.setBoardTypeUsing(id, boardTypeUsing);
        if (result.isResult() == false) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/board/type/count")
    public ResponseEntity<?> boardTypeCount() {
        List<BoardTypeCount> list = boardService.getBoardTypeCount();
        return ResponseEntity.ok().body(list);
    }

    @PatchMapping("/api/board/{id}/top")
    public ResponseEntity<?> boardPostTop(@PathVariable Long id) {
        ServiceResult result = boardService.setBoardTop(id, true);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/api/board/{id}/top/clear")
    public ResponseEntity<?> boardPostTopClear(@PathVariable Long id) {
        ServiceResult result = boardService.setBoardTop(id, false);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/api/board/{id}/publish")
    public ResponseEntity<?> boardPeriod(@PathVariable Long id, @RequestBody BoardPeriod boardPeriod) {
        ServiceResult result = boardService.setBoardPeriod(id, boardPeriod);

        if(!result.isResult()) {
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();
    }

    @PutMapping("/api/board/{id}/hits")
    public ResponseEntity<?> boardHits(@PathVariable Long id, @RequestHeader("F-TOKEN") String token) {
        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.setBoardHits(id, email);
        if(result.isFail()) {
            return ResponseResult.fail(result.getMessage());
        }

        return ResponseResult.success();
    }

    @PutMapping("/api/board/{id}/like")
    public ResponseEntity<?> boardLike(@PathVariable long id, @RequestHeader("F-TOKEN") String token) {
        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.setBoardLike(id, email);

        return ResponseResult.result(result);
    }

    @PutMapping("/api/board/{id}/unlike")
    public ResponseEntity<?> boardUnlike(@PathVariable long id, @RequestHeader("F-TOKEN") String token) {
        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.setBoardUnLike(id, email);

        return ResponseResult.result(result);
    }

    @PutMapping("/api/board/{id}/badreport")
    public ResponseEntity<?> boardBadReport(@PathVariable Long id
            , @RequestHeader("F-TOKEN") String token
            , @RequestBody BoardBadReportInput boardBadReportInput) {

        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.addBadReport(id, email, boardBadReportInput);
        return ResponseResult.result(result);

    }

    /**
     *  AOP의 Around를 이용하여 게시판 상세 조회에 대한 히스토리 기록하는 기능
     */
    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Board board = null;
        try {
            board = boardService.detail(id);
        } catch (BizException e) {
            return ResponseResult.fail(e.getMessage());
        }
        return ResponseResult.success(board);
    }
}
