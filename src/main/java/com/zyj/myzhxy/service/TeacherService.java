package com.zyj.myzhxy.service;

import com.zyj.myzhxy.pojo.LoginForm;
import com.zyj.myzhxy.pojo.Teacher;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TeacherService extends IService<Teacher> {

    /**
     * 登录校验，查询提交的表单是否有此人
     * @param loginForm 登录页提交的表单
     * @return
     */
    Teacher login(LoginForm loginForm);

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    Teacher getTeacherById(Long userId);

    /**
     * 获取教师信息(分页带条件)
     * @param page
     * @param teacher 教师名称，用于模糊查询
     * @return
     */
    IPage<Teacher> getTeachersByOpr(Page<Teacher> page, Teacher teacher);
}
