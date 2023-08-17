package com.example.myprojectbackend.filter;

import com.example.myprojectbackend.entity.RestBean;
import com.example.myprojectbackend.utils.Const;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:FlowLimitFilter
 * Package:com.example.myprojectbackend.filter
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/17 17:42
 * @Version 1.0
 */
//限流操作
@Component
@Order(Const.ORDER_LIMIT)
public class FlowLimitFilter extends HttpFilter {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        String address = request.getRemoteAddr();
        if(this.tryCount(address)){
            chain.doFilter(request,response);
        }else{

        }

    }

    private void writeBlockMessage(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(RestBean.forbidden("操作频繁,请稍后再试").asJsonString());
    }

    //对ip进行计数 redis实现
    public boolean tryCount(String ip){
        //若用户已被封禁
        synchronized (ip.intern()){
            if(Boolean.TRUE.equals(stringRedisTemplate.hasKey(Const.FLOW_LIMIT_BLOCK + ip))){
                return false;
            }
            return this.limitPeriodCheck(ip);
        }
    }

    private boolean limitPeriodCheck(String ip){
        if (stringRedisTemplate.hasKey(Const.FLOW_LIMIT_COUNTER+ip)) {
            long increment = Optional
                    .ofNullable(stringRedisTemplate.opsForValue().increment(Const.FLOW_LIMIT_COUNTER + ip))
                    .orElse(0L);
            if(increment>10){
                //封禁30s
                stringRedisTemplate.opsForValue().
                        set(Const.FLOW_LIMIT_BLOCK+ip,"",30,TimeUnit.SECONDS);
                return false;
            }
        }else {
            stringRedisTemplate.opsForValue().
                    set(Const.FLOW_LIMIT_COUNTER+ip,"1",3, TimeUnit.SECONDS);
        }
        return true;
    }
}
