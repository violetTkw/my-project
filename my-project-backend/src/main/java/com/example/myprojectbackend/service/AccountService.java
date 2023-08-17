package com.example.myprojectbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myprojectbackend.entity.dto.Account;
import com.example.myprojectbackend.entity.vo.request.ConfirmResetVO;
import com.example.myprojectbackend.entity.vo.request.EmailRegisterVO;
import com.example.myprojectbackend.entity.vo.request.EmailResetVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * ClassName:AccountService
 * Package:com.example.myprojectbackend.service
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/15 17:59
 * @Version 1.0
 */

public interface AccountService extends IService<Account> , UserDetailsService {
    Account findAccountByNameOrEmail(String text);
    String registerEmailVerifyCode(String type,String email,String ip);
    String registerEmailAccount(EmailRegisterVO vo);
    String resetConfirm(ConfirmResetVO vo);
    String resetEmailAccountPassword(EmailResetVO vo);
}
