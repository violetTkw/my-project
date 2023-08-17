package com.example.myprojectbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * ClassName:WebConfiguration
 * Package:com.example.myprojectbackend.config
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/15 18:32
 * @Version 1.0
 */
@Configuration
public class WebConfiguration {
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
