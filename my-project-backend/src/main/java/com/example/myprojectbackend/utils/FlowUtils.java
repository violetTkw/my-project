package com.example.myprojectbackend.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * ClassName:FlowUtils
 * Package:com.example.myprojectbackend.utils
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/16 17:38
 * @Version 1.0
 */
//限流
@Component
public class FlowUtils {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    //判断是否过期
    public boolean limitOnceCheck(String key,int blockTime){
        if(Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))){
            return false;
        }else {
            //冷却时间
            stringRedisTemplate.opsForValue().set(key,"",blockTime, TimeUnit.SECONDS);
        }
        return true;
    }
}
