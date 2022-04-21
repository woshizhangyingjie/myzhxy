package com.zyj.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhang
 * @Date 2022/4/18 - 10:59
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_admin")
public class Admin {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Character gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String portraitPath;  // 头像图片路径
}
