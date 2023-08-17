package com.example.myprojectbackend.utils;

/**
 * ClassName:Const
 * Package:com.example.myprojectbackend.utils
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/15 16:21
 * @Version 1.0
 */
public class Const {
    public static final String JWT_BLACK_LIST = "jwt:blacklist:";

    public static final int ORDER_CORS = -102;

    public static final int ORDER_LIMIT = -101;

    //邮件验证码
    public final static String VERIFY_EMAIL_LIMIT = "verify:email:limit:";
    public final static String VERIFY_EMAIL_DATA = "verify:email:data:";

    //计数器
    public final static String FLOW_LIMIT_COUNTER ="flow:counter:";
    //封禁的标识
    public final static String FLOW_LIMIT_BLOCK ="flow:block:";
}
