package me.helpeachother.springsecurity.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * <pre>
 * Class Name : PersistentLogins
 * Description : 스프링 시큐리티의 cookie token 을 저장하기 위한 entity
 * 테이블을 생성하는 용도로 사용되며, 실제 처리는 SecurityConfig.java 에 있는 JdbcTokenRepositoryImpl 에서 수행함
 *
 *
 *  Modification Information
 *  Modify Date 	Modifier		Comment
 *  -----------------------------------------------
 *  2021.03.24      swkim  		     New
 *
 * </pre>
 *
 * @author swkim
 * @since 2021.03.24
 */

@Entity
@Getter @Setter
@Table(name = "persistent_logins")
public class PersistentLogins {

    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false, length = 64)
    private LocalDateTime lastUsed;
}