package me.helpeachother.springbootmybatis.configuration;

import me.helpeachother.springbootmybatis.interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.nio.charset.Charset;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor());
    }

    @Bean
    public Filter characterEncodingFilter(){
        // CharacterEncodingFilter는 스프링이 제공하는 클래스로 웹에서 주고받는 데이터의 헤더값을 UTF-8로 인코딩해준다.
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        // CharacterEncodingFilter 기본값은 false.
        // false인 경우 getCharacterEncoding 메서드의 반환값이 null이 아닌 경우에만 인코딩을 변경하지 않는다.
        // 특정한 인코딩이 지정되지 않을 때만 인코딩을 변경한다.

        // forceEncoding이 true이면, 입력값과 결과값 모두에 강제적으로 설정된 인코딩으로 변경한다.
        characterEncodingFilter.setForceEncoding(true);

        return characterEncodingFilter;
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter(){
        // ResponseBody를 이용하여 결과를 출력할 때 그 결과를 UTF-8로 설정
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }
}
