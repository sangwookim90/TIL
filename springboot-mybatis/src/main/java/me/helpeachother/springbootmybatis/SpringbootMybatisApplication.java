package me.helpeachother.springbootmybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;


// MultipartResolver를 이미 등록했기 때문에 어플리케이션의 스프링 설정이 자동 구성되는 것을 사용하지 않도록 변경해야 한다.
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class SpringbootMybatisApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}
}
