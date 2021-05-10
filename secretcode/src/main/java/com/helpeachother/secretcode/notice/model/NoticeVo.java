package com.helpeachother.secretcode.notice.model;

import com.helpeachother.secretcode.notice.entity.Notice;
import com.helpeachother.secretcode.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeVo {
    private long id;
    private long regUserId;
    private String regUserName;
    private String title;
    private String contents;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private int hits;
    private int likes;

    public static NoticeVo of(Notice e) {
        return NoticeVo.builder()
                .id(e.getId())
                .regUserId(e.getUser().getId())
                .regUserName(e.getUser().getUserName())
                .title(e.getTitle())
                .contents(e.getContents())
                .regDate(e.getRegDate())
                .updateDate(e.getUpdateDate())
                .hits(e.getHits())
                .likes(e.getLikes())
                .build();
    }
}
