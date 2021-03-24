package me.helpeachother.springsecurity.account;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode
public class AccountVo {

    private String id;
    private String username;
    private String password;
    private String role;
}
