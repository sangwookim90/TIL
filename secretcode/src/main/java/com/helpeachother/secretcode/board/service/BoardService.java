package com.helpeachother.secretcode.board.service;

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
}
