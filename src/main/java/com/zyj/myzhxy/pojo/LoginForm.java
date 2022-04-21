package com.zyj.myzhxy.pojo;

/**
 * @Author zhang
 * @Date 2022/4/18 - 16:03
 * @Version 1.0
 */

import lombok.Data;

/**
 * @project: ssm_sms
 * @description: 用户登录表单信息
 */
@Data
public class LoginForm {

    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;

}
