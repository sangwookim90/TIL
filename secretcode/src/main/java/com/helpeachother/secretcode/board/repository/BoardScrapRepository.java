package com.helpeachother.secretcode.board.repository;

import com.helpeachother.secretcode.board.entity.BoardScrap;
import com.helpeachother.secretcode.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardScrapRepository extends JpaRepository<BoardScrap, Long> {

}
