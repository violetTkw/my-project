package com.example.myprojectbackend.entity.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * ClassName:EmailRegisterVO
 * Package:com.example.myprojectbackend.entity.vo.request
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/17 9:24
 * @Version 1.0
 */
@Data
public class EmailRegisterVO {
    @Email
    @Length(min=4)
    String email;

    @Length(max=6,min=6)
    String code;

    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    @Length(max=10,min=1)
    String username;

    @Length(min=6,max=20)
    String password;
}
