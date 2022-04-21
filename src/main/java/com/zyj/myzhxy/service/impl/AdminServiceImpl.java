package com.zyj.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyj.myzhxy.mapper.AdminMapper;
import com.zyj.myzhxy.pojo.Admin;
import com.zyj.myzhxy.pojo.LoginForm;
import com.zyj.myzhxy.service.AdminService;
import com.zyj.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @Author zhang
 * @Date 2022/4/18 - 16:06
 * @Version 1.0
 */
@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    /**
     * 登录验证，查询提交的表单是否有此人
     * @param loginForm 登录页提交的表单
     * @return
     */
    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername())
                        // 注意将密码转为密文
                        .eq("password", MD5.encrypt(loginForm.getPassword()));
        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    @Override
    public Admin getAdminById(Long userId) {
        Admin admin = baseMapper.selectById(userId);
        return admin;
    }

    /**
     * 获取管理员信息(分页带条件)
     * @param page
     * @param adminName 管理员名字，用于模糊查询
     * @return
     */
    @Override
    public IPage<Admin> getAdminByOpr(Page<Admin> page, String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        // 若adminName的条件不为空，则添加模糊查询的条件
        if(!StringUtils.isEmpty(adminName)){
            queryWrapper.like("name", adminName);
        }
        queryWrapper.orderByDesc("id");
        Page<Admin> adminPage = baseMapper.selectPage(page, queryWrapper);
        return adminPage;
    }

}
