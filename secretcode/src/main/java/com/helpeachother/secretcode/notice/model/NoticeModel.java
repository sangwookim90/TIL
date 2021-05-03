package com.helpeachother.secretcode.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeModel {

    // ID, 제목, 내용, 작성일
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime regDate;

}
