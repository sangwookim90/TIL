package me.helpeachother.springsecurity.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {

    @Id @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;
    private String role;

    public AccountVo getVo(ModelMapper mapper) {
        return mapper.map(this,AccountVo.class);
    }

    @Builder
    public Account(Integer id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

}
