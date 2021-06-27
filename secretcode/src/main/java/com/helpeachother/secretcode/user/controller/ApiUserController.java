package com.helpeachother.secretcode.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.helpeachother.secretcode.board.entity.Board;
import com.helpeachother.secretcode.board.entity.BoardComment;
import com.helpeachother.secretcode.board.service.BoardService;
import com.helpeachother.secretcode.common.model.ResponseResult;
import com.helpeachother.secretcode.notice.entity.Notice;
import com.helpeachother.secretcode.notice.entity.NoticeLike;
import com.helpeachother.secretcode.notice.model.NoticeVo;
import com.helpeachother.secretcode.notice.model.ResponseError;
import com.helpeachother.secretcode.notice.repository.NoticeLikeRepository;
import com.helpeachother.secretcode.notice.repository.NoticeRepository;
import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.exception.ExistsEmailException;
import com.helpeachother.secretcode.user.exception.PasswordNotMatchException;
import com.helpeachother.secretcode.user.exception.UserNotFoundException;
import com.helpeachother.secretcode.user.model.*;
import com.helpeachother.secretcode.user.repository.UserRepository;
import com.helpeachother.secretcode.util.JwtUtils;
import com.helpeachother.secretcode.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ApiUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeLikeRepository noticeLikeRepository;
    private final BoardService boardService;

    private String getEncryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    private String getResetPassword() {
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,10);
    }

    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        if(userRepository.countByEmail(userInput.getEmail()) > 0) {
            throw new ExistsEmailException();
        }

        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .password(getEncryptPassword(userInput.getPassword()))
                .phone(userInput.getPhone())
                .build();

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setPhone(userUpdate.getPhone());
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/api/user/{id}")
    public UserVo getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        //return new UserVo(user);
        return UserVo.of(user);
    }

    @GetMapping("/api/user/{id}/notice")
    public List<NoticeVo> userNotice(@PathVariable Long id) {

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        List<Notice> noticeList = noticeRepository.findByUser(user);
        List<NoticeVo> noticeVoList = new ArrayList<>();

        noticeList.stream().forEach((e) -> noticeVoList.add(NoticeVo.of(e)));

        return noticeVoList;
    }

    @PatchMapping("/api/user/{id}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody UserInputPassword userInputPassword, Errors errors) {
        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByIdAndPassword(id, userInputPassword.getPassword())
                .orElseThrow(PasswordNotMatchException::new);

        user.setPassword(userInputPassword.getNewPassword());
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        // 내가 쓴 공지사항이 있는 경우
        // 1. 삭제 못해.. 삭제하려면 공지사항 삭제하고 와
        // 2. 회원삭제전에 공지사항글을 다 삭제

        try {
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            String message = "제약조건에 문제가 발생하였습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String message = "회원 탈퇴 중 문제가 발생하였습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> findUser(@RequestBody UserInputFind userInputFind) {
        User user = userRepository.findByUserNameAndPhone(userInputFind.getUserName(), userInputFind.getPhone())
                .orElseThrow(UserNotFoundException::new);

        return ResponseEntity.ok().body(UserVo.of(user));
    }

    @GetMapping("/api/user/{id}/password/reset")
    public void resetUserPassword(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        user.setPassword(getEncryptPassword(getResetPassword()));
        userRepository.save(user);
    }

    @GetMapping("/api/user/{id}/notice/like")
    public List<NoticeLike> likeNotice(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        List<NoticeLike> noticeLikeList = noticeLikeRepository.findByUser(user);

        return noticeLikeList;
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors) {
        List<ResponseError> responseErrorList = new ArrayList<>();
        if(errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(userLogin.getEmail()).orElseThrow(UserNotFoundException::new);

        if(PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword()) == false) {
            throw new PasswordNotMatchException();
        }

        // 토큰 유효기간 1개월
        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = java.sql.Timestamp.valueOf(expiredDateTime);

        // 토큰발행시점
        String token = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim("user_id", user.getId())
                .withSubject(user.getUserName())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("passwordkey".getBytes()));

        return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());

    }

    @PatchMapping("/api/user/login")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String token = request.getHeader("F-TOKEN");
        String email = "";
        try {
            email = JWT.require(Algorithm.HMAC512("passwordkey".getBytes()))
                    .build()
                    .verify(token)
                    .getIssuer();
        } catch (SignatureVerificationException e) {
            throw new PasswordNotMatchException();
        } catch (Exception e) {
            throw new PasswordNotMatchException("토큰 발행에 실패하였습니다.");
        }

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        // 토큰 유효기간 1개월
        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = java.sql.Timestamp.valueOf(expiredDateTime);

        String newToken = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim("user_id", user.getId())
                .withSubject(user.getUserName())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("passwordkey".getBytes()));

        return ResponseEntity.ok().body(UserLoginToken.builder().token(newToken).build());
    }

    @DeleteMapping("/api/user/login")
    public ResponseEntity<?> removeToken(@RequestHeader("F-TOKEN") String token) {
        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        // 세션 또는 쿠키 삭제
        // 블랙리스트 작성
        // 클라이언트 쿠키 / 로컬스토리지 / 세션 스토리지 삭제

        return ResponseEntity.ok().build();

    }

    /**
     * 내가 작성한 게시글 목록을 리턴하는 API
     */
    @GetMapping("/api/user/board/post")
    public ResponseEntity<?> myPost(@RequestHeader("F-TOKEN") String token) {
        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        List<Board> boardList = boardService.postList(email);
        return ResponseResult.success(boardList);

    }

    /**
     * 내가 작성한 코멘트 목록을 리턴하는 API
     */
    @GetMapping("/api/user/board/comments")
    public ResponseEntity<?> myComments(@RequestHeader("F-TOKEN") String token) {

        String email = "";
        try {
            email = JwtUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        List<BoardComment> boardCommentList = boardService.commentList(email);
        return ResponseResult.success(boardCommentList);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundExceptionHandler(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistsEmailException.class)
    public ResponseEntity<?> ExistsEmailExceptionHandler(ExistsEmailException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> PasswordExceptionHandler(PasswordNotMatchException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
