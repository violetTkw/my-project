package com.example.myprojectbackend.filter;

import com.example.myprojectbackend.utils.Const;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ClassName:CorsFilter
 * Package:com.example.myprojectbackend.filter
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/16 15:05
 * @Version 1.0
 */
@Component
@Order(Const.ORDER_CORS)
//跨域请求
public class CorsFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        this.addCorsHeader(request,response);
        //放行
        chain.doFilter(request,response);
    }
    //添加响应头
    private void addCorsHeader(HttpServletRequest request,HttpServletResponse response){
        //允许哪些地址进行访问
        response.addHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Methods","GET ,POST, PUT, DELETE ,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers","Authorization,Content-Type");

    }
}
