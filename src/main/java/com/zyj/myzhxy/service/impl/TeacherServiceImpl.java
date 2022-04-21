package com.zyj.myzhxy.service.impl;

import com.zyj.myzhxy.mapper.TeacherMapper;
import com.zyj.myzhxy.pojo.Admin;
import com.zyj.myzhxy.pojo.LoginForm;
import com.zyj.myzhxy.pojo.Teacher;
import com.zyj.myzhxy.service.TeacherService;
import com.zyj.myzhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("teaService")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    /**
     * 登录校验，查询提交的表单是否有此人
     * @param loginForm 登录页提交的表单
     * @return
     */
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername())
                // 注意将密码转为密文
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    @Override
    public Teacher getTeacherById(Long userId) {
        Teacher teacher = baseMapper.selectById(userId);
        return teacher;
    }

    /**
     * 获取教师信息(分页带条件)
     * @param page
     * @param teacher 教师名称，用于模糊查询
     * @return
     */
    @Override
    public IPage<Teacher> getTeachersByOpr(Page<Teacher> page, Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        // 若教师名称不为空，则添加模糊查询条件
        if(!StringUtils.isEmpty(teacher.getName())){
            queryWrapper.like("name", teacher.getName());
        }
        // 若班级名称不为空，则添加查询条件
        if(!StringUtils.isEmpty(teacher.getClazzName())){
            queryWrapper.eq("clazz_name", teacher.getClazzName());
        }
        queryWrapper.orderByDesc("id");
        Page<Teacher> teacherPage = baseMapper.selectPage(page, queryWrapper);
        return teacherPage;
    }

}
