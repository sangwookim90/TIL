package com.helpeachother.secretcode.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardBadReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long userId;

    @Column
    private String userName;

    @Column
    private String userEmail;

    @Column
    private long boardId;

    @Column
    private long boardUserId;

    @Column
    private String boardTitle;

    @Column
    private String boardContents;

    @Column
    private LocalDateTime boardRegDate;

    @Column
    private String comments;

    @Column
    private LocalDateTime regDate;

}
