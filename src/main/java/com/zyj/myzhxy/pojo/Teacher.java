package com.zyj.myzhxy.pojo;

/**
 * @Author zhang
 * @Date 2022/4/18 - 11:30
 * @Version 1.0
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: sms
 * @description: 教师信息
 */
@Data
@TableName("tb_teacher")
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String tno;
    private String name;
    private char gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String clazzName;
    private String portraitPath;//存储头像的项目路径

}