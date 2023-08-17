package com.example.myprojectbackend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName:Account
 * Package:com.example.myprojectbackend.entity.dto
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/15 17:42
 * @Version 1.0
 */
@Data
@TableName("db_account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @TableId(type = IdType.AUTO)
    Integer id;

    String username;

    String password;

    String email;

    String role;

    Date registerTime;
}
