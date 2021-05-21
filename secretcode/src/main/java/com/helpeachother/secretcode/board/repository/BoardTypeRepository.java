package com.helpeachother.secretcode.board.repository;

import com.helpeachother.secretcode.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardTypeRepository extends JpaRepository<BoardType, Long> {

    Optional<BoardType> findByBoardName(String boardName);

}
