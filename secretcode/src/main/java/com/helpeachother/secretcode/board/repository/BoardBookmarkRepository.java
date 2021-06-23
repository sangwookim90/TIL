package com.helpeachother.secretcode.board.repository;

import com.helpeachother.secretcode.board.entity.BoardBookmark;
import com.helpeachother.secretcode.board.entity.BoardScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardBookmarkRepository extends JpaRepository<BoardBookmark, Long> {

}
