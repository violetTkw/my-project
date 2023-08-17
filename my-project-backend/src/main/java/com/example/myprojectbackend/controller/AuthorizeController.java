package com.example.myprojectbackend.controller;

import com.example.myprojectbackend.entity.RestBean;
import com.example.myprojectbackend.entity.vo.request.ConfirmResetVO;
import com.example.myprojectbackend.entity.vo.request.EmailRegisterVO;
import com.example.myprojectbackend.entity.vo.request.EmailResetVO;
import com.example.myprojectbackend.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

/**
 * ClassName:AuthorizeController
 * Package:com.example.myprojectbackend.controller
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/16 18:25
 * @Version 1.0
 */
@Validated//校验
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    @Resource
    AccountService service;

    @GetMapping("/ask-code")
    public RestBean<Void> askVerifyCode(@RequestParam @Email String email,
                                        @RequestParam @Pattern(regexp = "(register|reset)") String type,
                                        HttpServletRequest request){
        return this.messageHandle(() ->
                service.registerEmailVerifyCode(type,email,request.getRemoteAddr()));
    }

    @PostMapping("/register")
    public RestBean<Void> register(@RequestBody @Valid EmailRegisterVO vo){
        return this.messageHandle(() ->
                service.registerEmailAccount(vo));
    }

    @PostMapping("/reset-confirm")
    public RestBean<Void> resetConfirm(@RequestBody @Valid ConfirmResetVO vo){
        return this.messageHandle(() -> service.resetConfirm(vo));
    }

    @PostMapping("/reset-password")
    public RestBean<Void> resetPassword(@RequestBody @Valid EmailResetVO vo){
        return this.messageHandle(() -> service.resetEmailAccountPassword(vo));
    }

    private RestBean<Void> messageHandle(Supplier<String> action){
        String message = action.get();
        return message==null?RestBean.success():RestBean.failure(400,message);
    }

}
