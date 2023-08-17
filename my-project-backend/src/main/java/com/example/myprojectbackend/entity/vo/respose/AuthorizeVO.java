package com.example.myprojectbackend.entity.vo.respose;

import lombok.Data;

import java.util.Date;

/**
 * ClassName:AuthorizeVO
 * Package:com.example.myprojectbackend.entity.vo.respose
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/14 20:33
 * @Version 1.0
 */
@Data
public class AuthorizeVO {
    String username;

    String role;

    String token;

    Date expire;
}
