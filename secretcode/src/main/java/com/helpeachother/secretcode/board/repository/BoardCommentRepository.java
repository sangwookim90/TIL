package com.helpeachother.secretcode.board.repository;

import com.helpeachother.secretcode.board.entity.BoardComment;
import com.helpeachother.secretcode.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    List<BoardComment> findByUser(User user);

}
