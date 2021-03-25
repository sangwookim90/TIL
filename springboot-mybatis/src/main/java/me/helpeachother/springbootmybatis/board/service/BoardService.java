package me.helpeachother.springbootmybatis.board.service;

import me.helpeachother.springbootmybatis.board.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> selectBoardList() throws Exception;

    void insertBoard(BoardDto board) throws Exception;

    BoardDto selectBoardDetail(int boardIdx) throws Exception;

    void deleteBoard(int boardIdx) throws Exception;

    void updateBoard(BoardDto board) throws Exception;
}
