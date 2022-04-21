package com.zyj.myzhxy.service;

import com.zyj.myzhxy.pojo.LoginForm;
import com.zyj.myzhxy.pojo.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


public interface StudentService extends IService<Student> {

    /**
     * 登录校验，查询提交的表单是否有此人
     * @param loginForm 登录页提交的表单
     * @return
     */
    Student login(LoginForm loginForm);

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    Student getStdentById(Long userId);

    /**
     * 查询学生信息(分页带条件)
     * @param page
     * @param student 分页查询的条件
     * @return
     */
    IPage<Student> getStudentByOpr(Page<Student> page, Student student);
}
