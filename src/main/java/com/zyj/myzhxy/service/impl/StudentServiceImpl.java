package com.zyj.myzhxy.service.impl;

import com.zyj.myzhxy.mapper.StudentMapper;
import com.zyj.myzhxy.pojo.Admin;
import com.zyj.myzhxy.pojo.LoginForm;
import com.zyj.myzhxy.pojo.Student;
import com.zyj.myzhxy.service.StudentService;
import com.zyj.myzhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**

 */
@Service("stuService")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    /**
     * 登录校验，查询提交的表单是否有此人
     * @param loginForm 登录页提交的表单
     * @return
     */
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername())
                        // 注意将密码转为密文
                        .eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    @Override
    public Student getStdentById(Long userId) {
        Student student = baseMapper.selectById(userId);
        return student;
    }

    /**
     * 查询学生信息(分页带条件)
     * @param page
     * @param student 分页查询的条件
     * @return
     */
    @Override
    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();

        // 若分页的查询条件不为空，则添加条件
        String name = student.getName();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name", name);
        }
        String clazzName = student.getClazzName();
        if(!StringUtils.isEmpty(clazzName)){
            queryWrapper.eq("clazz_name", clazzName);
        }
        queryWrapper.orderByDesc("id");
        Page<Student> studentPage = baseMapper.selectPage(page, queryWrapper);
        return studentPage;
    }

}
