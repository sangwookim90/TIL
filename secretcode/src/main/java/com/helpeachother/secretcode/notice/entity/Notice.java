package com.helpeachother.secretcode.notice.entity;

import com.helpeachother.secretcode.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime updateDate;

    @Column
    private int hits;

    @Column
    private int likes;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Column
    private LocalDateTime deletedDate;
}
