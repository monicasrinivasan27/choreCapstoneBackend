//package org.launchcode.taskcrusher;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class ParentWebApplicationConfig implements WebMvcConfigurer {
//
//    // Creates object that will allow app to access filer
//    @Bean
//    //Makes is a Spring-managed class
//    public ParentAuthenticationFilter parentAuthenticationFilter() {
//        return new ParentAuthenticationFilter();
//    }
//
//    //Register filter with Spring so it will intercept requests
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor( parentAuthenticationFilter());
//    }
//}
