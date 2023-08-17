package com.example.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myprojectbackend.entity.dto.Account;
import com.example.myprojectbackend.entity.vo.request.ConfirmResetVO;
import com.example.myprojectbackend.entity.vo.request.EmailRegisterVO;
import com.example.myprojectbackend.entity.vo.request.EmailResetVO;
import com.example.myprojectbackend.mapper.AccountMapper;
import com.example.myprojectbackend.service.AccountService;
import com.example.myprojectbackend.utils.Const;
import com.example.myprojectbackend.utils.FlowUtils;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:AccountServiceImpl
 * Package:com.example.myprojectbackend.service.impl
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/15 18:00
 * @Version 1.0
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Resource
    FlowUtils utils;

    @Resource
    AmqpTemplate amqpTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    PasswordEncoder encoder;

    /**
     * 从数据库中通过用户名或邮箱查找用户详细信息
     * @param username
     * @return 用户详细信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account =this.findAccountByNameOrEmail(username);
        if(account==null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(username)
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }

    @Override
    public Account findAccountByNameOrEmail(String text){
        return this.query()
                .eq("username",text).or()
                .eq("email",text)
                .one();
    }

    /**
     * 邮件验证码注册账号操作，需要检查验证码是否正确以及邮箱、用户名是否存在重名
     * @return 操作结果，null表示正常，否则为错误原因
     */
    @Override
    public String registerEmailVerifyCode(String type, String email, String ip) {
        //加锁 防止请求过多
        synchronized (ip.intern()){
            //请求不通过
            if(!this.verifyLimit(ip)){
                return "请求频繁,请稍后再试";
            }
            //生成随机验证码
            Random random = new Random();
            int code = random.nextInt(89999) + 100000;
            Map<String,Object> data = Map.of("type",type,"email",email,"code",code);
            //将数据放入消息队列中
            amqpTemplate.convertAndSend("email",data);
            //放入redis中
            stringRedisTemplate.opsForValue()
                    .set(Const.VERIFY_EMAIL_DATA+email,String.valueOf(code),3, TimeUnit.MINUTES);
            return null;
        }
    }



    //限制用户 60s只能发送一次
    private boolean verifyLimit(String ip){
        String key = Const.VERIFY_EMAIL_LIMIT+ip;
        return utils.limitOnceCheck(key,60);
    }

    @Override
    public String registerEmailAccount(EmailRegisterVO vo) {
        //判断是否被注册 校验验证码
        String email = vo.getEmail();
        String username = vo.getUsername();
        String key = Const.VERIFY_EMAIL_DATA+email;
        String code = stringRedisTemplate.opsForValue().get(key);
        if(code==null){
            return "请先获取验证码";
        }
        if(!code.equals(vo.getCode())){
            return "验证码输入错误,请重新输入";
        }
        if(this.existAccountByEmail(email)){
            return "此邮件已被其他用户注册";
        }
        if(this.existAccountByUsername(username)){
            return "此用户名已被其他用户注册,请更换用户名";
        }
        String password = encoder.encode(vo.getPassword());
        Account account = new Account(null,username,password,email,"user",new Date());
        if (this.save(account)) {
            stringRedisTemplate.delete(key);
            return null;
        }else {
            return "内部错误,请联系管理员";
        }
    }

    //判断邮件是否被注册
    private boolean existAccountByEmail(String email){
        return this.baseMapper.exists(Wrappers.<Account>query().eq("email",email));
    }
    private boolean existAccountByUsername(String username){
        return this.baseMapper.exists(Wrappers.<Account>query().eq("username",username));
    }

    //重置密码前的验证
    @Override
    public String resetConfirm(ConfirmResetVO vo) {
        String email = vo.getEmail();
        String code = stringRedisTemplate.opsForValue().get(Const.VERIFY_EMAIL_DATA + email);
        if(code==null){
            return "请先获取验证码";
        }
        if(!code.equals(vo.getCode())){
            return "验证码输入错误,请重新输入";
        }
        return null;
    }

    //重置密码
    @Override
    public String resetEmailAccountPassword(EmailResetVO vo) {
        String email = vo.getEmail();
        String verify = this.resetConfirm(new ConfirmResetVO(email, vo.getCode()));
        //验证错误
        if(verify!=null){
            return verify;
        }
        String password = encoder.encode(vo.getPassword());
        boolean update = this.update().eq("email", email).set("password", password).update();
        if(update){
            stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + email);
        }
        return null;
    }
}
