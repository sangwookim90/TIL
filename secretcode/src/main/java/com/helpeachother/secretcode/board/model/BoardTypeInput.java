package com.helpeachother.secretcode.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardTypeInput {

    @NotBlank(message = "게시판 이름은 필수항목 입니다.")
    private String name;

}
