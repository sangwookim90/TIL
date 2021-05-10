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
    public HttpMessageConverter<String> responseBodyConverter(){
        // ResponseBody를 이용하여 결과를 출력할 때 그 결과를 UTF-8로 설정
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        // 바이트 단위, 5mb
        commonsMultipartResolver.setMaxUploadSizePerFile(5*1024*1024);
        return commonsMultipartResolver;
    }
}
