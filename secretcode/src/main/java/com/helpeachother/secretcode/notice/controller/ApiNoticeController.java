package com.helpeachother.secretcode.notice.controller;

import com.helpeachother.secretcode.notice.model.Notice;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiNoticeController {

    @GetMapping("/api/notice")
    public Notice notice() {

        Notice notice = new Notice();
        notice.setId(1L);
        notice.setTitle("공지사항");
        notice.setContents("공지사항입니다.");
        notice.setRegDate(LocalDateTime.of(2021, 4,24,10,5));

        return notice;
    }

    @GetMapping("/api/notices")
    public List<Notice> notices() {

        List<Notice> notices = new ArrayList<>();

        notices.add(Notice.builder().title("공지1")
                .contents("공지1 내용")
                .regDate(LocalDateTime.of(2021, 4,24,10,5)).build());

        notices.add(Notice.builder().title("공지2")
                .contents("공지2 내용")
                .regDate(LocalDateTime.of(2021, 4,24,10,15)).build());

        return notices;


    }

    @GetMapping("/api/notice/count")
    public int noticeCount() {

        return 10;
    }

    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody Notice notice) {
        notice.setId(3L);
        notice.setRegDate(LocalDateTime.now());

        return notice;
    }
}
