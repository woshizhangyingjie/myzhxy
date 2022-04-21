package com.zyj.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyj.myzhxy.pojo.Admin;
import com.zyj.myzhxy.pojo.LoginForm;

/**
 * @Author zhang
 * @Date 2022/4/18 - 16:05
 * @Version 1.0
 */
public interface AdminService extends IService<Admin> {

    /**
     * 登录校验，查询提交的表单是否有此人
     * @param loginForm 登录页提交的表单
     * @return
     */
    Admin login(LoginForm loginForm);

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    Admin getAdminById(Long userId);

    /**
     * 获取管理员信息(分页带条件)
     * @param page
     * @param adminName 管理员名字，用于模糊查询
     * @return
     */
    IPage<Admin> getAdminByOpr(Page<Admin> page, String adminName);
}
