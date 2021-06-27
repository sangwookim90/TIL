package com.helpeachother.secretcode.board.service;

import com.helpeachother.secretcode.board.entity.Board;
import com.helpeachother.secretcode.board.entity.BoardBadReport;
import com.helpeachother.secretcode.board.entity.BoardComment;
import com.helpeachother.secretcode.board.entity.BoardType;
import com.helpeachother.secretcode.board.model.*;
import com.helpeachother.secretcode.common.model.ServiceResult;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    ServiceResult addBoard(BoardTypeInput boardTypeInput);
    ServiceResult updateBoardType(Long id, BoardTypeInput boardTypeInput);
    Optional<BoardType> getByName(String boardName);
    ServiceResult deleteBoard(Long id);
    List<BoardType> getAllBoardType();
    ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing);
    List<BoardTypeCount> getBoardTypeCount();
    ServiceResult setBoardTop(Long id, boolean flag);
    ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod);
    ServiceResult setBoardHits(Long id, String email);
    ServiceResult setBoardLike(long id, String email);
    ServiceResult setBoardUnLike(long id, String email);
    ServiceResult addBadReport(Long id, String email, BoardBadReportInput boardBadReportInput);
    List<BoardBadReport> badReportList();
    ServiceResult scrapBoard(long id, String email);
    ServiceResult deleteBoardScrap(long id, String email);
    ServiceResult addBookmark(long id, String email);
    ServiceResult removeBookmark(long id, String email);

    /**
     * 내가 작성한 게시글 조회
     * @param email
     * @return
     */
    List<Board> postList(String email);

    /**
     * 내가 작성한 코멘트 조회
     * @param email
     * @return
     */
    List<BoardComment> commentList(String email);
}
