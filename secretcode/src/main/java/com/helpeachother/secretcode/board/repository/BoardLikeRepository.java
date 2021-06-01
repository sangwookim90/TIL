package com.helpeachother.secretcode.board.repository;

import com.helpeachother.secretcode.board.entity.Board;
import com.helpeachother.secretcode.board.entity.BoardLike;
import com.helpeachother.secretcode.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    long countByBoardAndUser(Board board, User user);
}
