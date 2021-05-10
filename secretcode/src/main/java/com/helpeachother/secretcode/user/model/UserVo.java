package com.helpeachother.secretcode.user.model;


import com.helpeachother.secretcode.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserVo {
    private long id;
    private String email;
    private String userName;
    private String phone;

    public UserVo(User user) {
        id = user.getId();
        email = user.getEmail();
        userName = user.getUserName();
        phone = user.getPhone();
    }

    public static UserVo of(User user) {
        return UserVo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .phone(user.getPhone())
                .build();
    }
}
