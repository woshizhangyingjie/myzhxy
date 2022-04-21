package com.zyj.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyj.myzhxy.pojo.Teacher;
import com.zyj.myzhxy.service.TeacherService;
import com.zyj.myzhxy.util.MD5;
import com.zyj.myzhxy.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/4/18 - 16:11
 * @Version 1.0
 */
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 获取教师信息(分页带条件)
     * @param pageNo 分页查询的页码
     * @param pageSize 分页查询每页的数据量
     * @param teacher 教师名称，用于模糊查询
     * @return
     */
    @ApiOperation("获取教师信息(分页带条件)")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("分页查询的页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询每页的数据量") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("教师名称，用于模糊查询") Teacher teacher
    ){
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        IPage<Teacher> iPage = teacherService.getTeachersByOpr(page, teacher);
        return Result.ok(iPage);
    }

    /**
     * 添加或修改教师信息
     * @param teacher 要保存或修改的JSON格式的教师信息
     * @return
     */
    @ApiOperation("添加或修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("要保存或修改的JSON格式的教师信息") @RequestBody Teacher teacher
    ){
        // 如果是添加教师，将密码转为密文
        if(teacher.getId() == null || teacher.getId() == 0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    /**
     * 删除或批量删除教师信息
     * @param ids 要删除的教师id的JSON数组
     * @return
     */
    @ApiOperation("删除或批量删除教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @ApiParam("要删除的教师id的JSON数组") @RequestBody List<Integer> ids
    ){
        teacherService.removeByIds(ids);
        return Result.ok();
    }

}
