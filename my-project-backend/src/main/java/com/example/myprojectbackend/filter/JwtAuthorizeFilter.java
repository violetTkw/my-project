package com.example.myprojectbackend.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.myprojectbackend.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * ClassName:JwtAuthorizeFilter
 * Package:com.example.myprojectbackend.filter
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/15 9:58
 * @Version 1.0
 */
@Component
public class JwtAuthorizeFilter extends OncePerRequestFilter {

    @Resource
    JwtUtils utils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization =request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if(jwt!=null){
            UserDetails user = utils.toUser(jwt);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            request.setAttribute("id",utils.toId(jwt));
        }
        filterChain.doFilter(request,response);
    }
}
