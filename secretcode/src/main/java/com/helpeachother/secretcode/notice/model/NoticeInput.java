package com.helpeachother.secretcode.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeInput {

    //제목, 내용
    @NotBlank(message = "제목은 필수 항목입니다.")
    @Size(min = 10, max = 100, message = "제목은 10-100자 사이의 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 항목입니다.")
    @Size(min = 50, max = 1000, message = "내용은 50-1000자 사이의 값입니다.")
    private String contents;

    @NotNull
    private long userId;

}
