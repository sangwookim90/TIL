package com.helpeachother.secretcode.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogCount {

    private long id;
    private String email;
    private String userName;
    private long noticeCount;
    private long noticeLikeCount;
}
