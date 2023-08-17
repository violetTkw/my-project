package com.example.myprojectbackend.entity.vo.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * ClassName:EmailResetVO
 * Package:com.example.myprojectbackend.entity.vo.request
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/17 11:23
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResetVO {
    @Email
    String email;

    @Length(max=6,min=6)
    String code;

    @Length(min=5,max=20)
    String password;
}
