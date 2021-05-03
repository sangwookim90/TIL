package com.helpeachother.secretcode.notice.controller;

import com.helpeachother.secretcode.notice.entity.Notice;
import com.helpeachother.secretcode.notice.model.NoticeInput;
import com.helpeachother.secretcode.notice.model.NoticeModel;
import com.helpeachother.secretcode.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ApiNoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/api/notice")
    public NoticeModel notice() {

        NoticeModel noticeModel = new NoticeModel();
        noticeModel.setId(1L);
        noticeModel.setTitle("공지사항");
        noticeModel.setContents("공지사항입니다.");
        noticeModel.setRegDate(LocalDateTime.of(2021, 4,24,10,5));

        return noticeModel;
    }

    @GetMapping("/api/notices")
    public List<NoticeModel> notices() {

        List<NoticeModel> noticeModels = new ArrayList<>();

        noticeModels.add(NoticeModel.builder().title("공지1")
                .contents("공지1 내용")
                .regDate(LocalDateTime.of(2021, 4,24,10,5)).build());

        noticeModels.add(NoticeModel.builder().title("공지2")
                .contents("공지2 내용")
                .regDate(LocalDateTime.of(2021, 4,24,10,15)).build());

        return noticeModels;


    }

    @GetMapping("/api/notice/count")
    public int noticeCount() {

        return 10;
    }

    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody NoticeInput noticeInput) {
        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .hits(0)
                .likes(0)
                .build();

        noticeRepository.save(notice);

        return notice;
    }

    @GetMapping("/api/notice/{id}")
    public Notice notice(@PathVariable Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
        }
        return null;
    }

    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if(notice.isPresent()) {
            notice.get().setTitle(noticeInput.getTitle());
            notice.get().setContents(noticeInput.getContents());
            notice.get().setUpdateDate(LocalDateTime.now());
            noticeRepository.save(notice.get());
        }
    }
}
