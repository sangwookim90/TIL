package com.helpeachother.secretcode.board.service;

import com.helpeachother.secretcode.board.entity.BoardType;
import com.helpeachother.secretcode.board.exception.BoardTypeNotFoundException;
import com.helpeachother.secretcode.board.model.BoardTypeInput;
import com.helpeachother.secretcode.board.model.BoardTypeUsing;
import com.helpeachother.secretcode.board.model.ServiceResult;
import com.helpeachother.secretcode.board.repository.BoardRepository;
import com.helpeachother.secretcode.board.repository.BoardTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardTypeRepository boardTypeRepository;
    private final BoardRepository boardRepository;

    @Override
    public ServiceResult addBoard(BoardTypeInput boardTypeInput) {
        Optional<BoardType> boardType = getByName(boardTypeInput.getName());
        if(boardType.isPresent() && boardTypeInput.getName().equals(boardType.get().getBoardName())) {
            // 동일한 게시판명이 존재함
            return ServiceResult.fail("이미 동일한 게시판 제목이 존재합니다.");

        }

        BoardType addBoardType = BoardType.builder()
                .boardName(boardTypeInput.getName())
                .regDate(LocalDateTime.now())
                .build();
        boardTypeRepository.save(addBoardType);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult updateBoardType(Long id, BoardTypeInput boardTypeInput) {
        Optional<BoardType> optionalBoardType = getByName(boardTypeInput.getName());
        if(optionalBoardType.isPresent()) {
            return ServiceResult.fail("수정할 이름과 동일한 게시판이 존재합니다.");
        }

        BoardType boardType = boardTypeRepository.findById(id).orElseThrow(BoardTypeNotFoundException::new);

        boardType.setBoardName(boardTypeInput.getName());
        boardType.setUpdateDate(LocalDateTime.now());

        boardTypeRepository.save(boardType);
        return ServiceResult.success();
    }

    @Override
    public Optional<BoardType> getByName(String boardName) {
        return boardTypeRepository.findByBoardName(boardName);
    }

    @Override
    public ServiceResult deleteBoard(Long id) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if(optionalBoardType.isPresent() == false) {
            return ServiceResult.fail("삭제할 게시판타입이 없습니다.");
        }
        BoardType boardType = optionalBoardType.get();

        if(boardRepository.countByBoardType(boardType) > 0) {
            return ServiceResult.fail("삭제할 게시판타입에 게시글이 존재합니다.");
        }

        boardTypeRepository.deleteById(id);
        return ServiceResult.success();
    }

    @Override
    public List<BoardType> getAllBoardType() {
        return boardTypeRepository.findAll();
    }

    @Override
    public ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if(optionalBoardType.isPresent() == false) {
            return ServiceResult.fail("게시판타입이 없습니다.");
        }

        BoardType boardType = optionalBoardType.get();
        boardType.setUsingYn(boardTypeUsing.isUsingYn());
        boardType.setUpdateDate(LocalDateTime.now());
        boardTypeRepository.save(boardType);

        return ServiceResult.success();
    }
}
