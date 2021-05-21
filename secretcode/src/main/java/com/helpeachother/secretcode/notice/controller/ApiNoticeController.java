package com.helpeachother.secretcode.notice.controller;

import com.helpeachother.secretcode.notice.entity.Notice;
import com.helpeachother.secretcode.notice.exception.AlreadyDeletedException;
import com.helpeachother.secretcode.notice.exception.DuplicateNoticeException;
import com.helpeachother.secretcode.notice.exception.NoticeNotFoundException;
import com.helpeachother.secretcode.notice.model.NoticeDeleteInput;
import com.helpeachother.secretcode.notice.model.NoticeInput;
import com.helpeachother.secretcode.notice.model.ResponseError;
import com.helpeachother.secretcode.notice.repository.NoticeRepository;
import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.exception.UserNotFoundException;
import com.helpeachother.secretcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiNoticeController {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @PostMapping("/api/notice")
    public ResponseEntity<Object> addNotice(@RequestBody @Valid NoticeInput noticeInput, Errors errors) {

        // Validation
        if(errors.hasErrors()) {
            List<ResponseError> responseErrors = new ArrayList<>();
            errors.getAllErrors().stream().forEach(e-> {
                responseErrors.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        // 중복 체크
        LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);

//        Optional<List<Notice>> noticeList = noticeRepository.findByTitleAndContentsAndRegDateIsGreaterThanEqual(
//                noticeInput.getTitle(),
//                noticeInput.getContents(),
//                checkDate);
//        if(noticeList.isPresent()) {
//            if (noticeList.get().size() > 0) {
//                throw new DuplicateNoticeException("동일한 내용의 공지사항이 존재합니다.");
//            }
//        }
        int noticeCount = noticeRepository.countByTitleAndContentsAndRegDateIsGreaterThanEqual(
                noticeInput.getTitle(),
                noticeInput.getContents(),
                checkDate);
        if(noticeCount > 0) {
            throw new DuplicateNoticeException("동일한 내용의 공지사항이 존재합니다.");
        }

        User writer = userRepository.findById(noticeInput.getUserId()).orElseThrow(UserNotFoundException::new);

        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .hits(0)
                .likes(0)
                .deleted(false)
                .user(writer)
                .build());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/notice/{id}")
    public Notice notice(@PathVariable Long id) {
        return noticeRepository.findById(id).orElseThrow(NoticeNotFoundException::new);
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<String> handlerNoticeNotFoundException(NoticeNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<String> handlerAlreadyDeletedException(AlreadyDeletedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateNoticeException.class)
    public ResponseEntity<?> handlerDuplicateNoticeException(DuplicateNoticeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {

//        Optional<Notice> notice = noticeRepository.findById(id);
//        if(!notice.isPresent()) {
//            // 예외 발생
//            throw new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다.");
//        }

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        notice.setTitle(noticeInput.getTitle());
        notice.setContents(noticeInput.getContents());
        notice.setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    @PatchMapping("/api/notice/{id}/hits")
    public void noticeHits(@PathVariable Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(NoticeNotFoundException::new);

        notice.setHits(notice.getHits()+1);
        noticeRepository.save(notice);
    }

    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(NoticeNotFoundException::new);

        if(notice.isDeleted()) {
            throw new AlreadyDeletedException("이미 삭제된 글입니다.");
        }

        notice.setDeleted(true);
        notice.setDeletedDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    @DeleteMapping("/api/notice")
    public void deleteNoticeList(@RequestBody NoticeDeleteInput noticeDeleteInput) {
        List<Notice> noticeList = noticeRepository.findByIdIn(noticeDeleteInput.getIdList())
                .orElseThrow(NoticeNotFoundException::new);

        noticeList.stream().forEach(e ->{
            e.setDeleted(true);
            e.setDeletedDate(LocalDateTime.now());
        });

        noticeRepository.saveAll(noticeList);
    }

    @DeleteMapping("/api/notice/all")
    public void deleteAll() {
        noticeRepository.deleteAll();
    }

    @GetMapping("/api/notice/latest/{size}")
    public Page<Notice> noticeLatest(@PathVariable int size) {
        return noticeRepository.findAll(PageRequest.of(0, size, Sort.Direction.DESC, "regDate"));
    }
}
