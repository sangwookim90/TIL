package me.helpeachother.springbootmybatis.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardFileDto {
    private int idx;
    private int boardIdx;
    private String originalFileName;
    private String storeFilePath;
    private long fileSize;
}
