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
public class BoardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String boardName;

    @Column
    private boolean usingYn;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime updateDate;

}
