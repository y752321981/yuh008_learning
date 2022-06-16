package cn.edu.ujn.yuh008.config;

import cn.edu.ujn.yuh008.common.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    String[] excludePathPatterns = {
            "/api/v1/login/loginCheck",
            "/api/v1/login/logout",
            "/api/v1/login/register"
    };
    @Bean
    public MyInterceptor getMyInterceptor(){
        return  new MyInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor()).excludePathPatterns(excludePathPatterns);
    }
}
