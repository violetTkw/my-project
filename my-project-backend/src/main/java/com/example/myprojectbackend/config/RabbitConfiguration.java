package com.example.myprojectbackend.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:RabbitConfiguration
 * Package:com.example.myprojectbackend.config
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/16 17:22
 * @Version 1.0
 */
@Configuration
public class RabbitConfiguration {
    //注册一个队列
    @Bean("emailQueue")
    public Queue emailQueue(){
        return QueueBuilder
                .durable("mail")
                .build();
    }
}
