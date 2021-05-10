package com.helpeachother.secretcode.notice.repository;

import com.helpeachother.secretcode.notice.entity.NoticeLike;
import com.helpeachother.secretcode.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeLikeRepository extends JpaRepository<NoticeLike, Long> {

    List<NoticeLike> findByUser(User user);

}
