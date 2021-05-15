package com.helpeachother.secretcode.user.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserLoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long userId;
    @Column
    private String email;
    @Column
    private String userName;
    @Column
    private LocalDateTime loginDate;
    @Column
    private String ipAddr;
}
