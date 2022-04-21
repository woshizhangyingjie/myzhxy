package com.zyj.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyj.myzhxy.pojo.Student;
import com.zyj.myzhxy.service.StudentService;
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
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 查询学生信息(分页带条件)
     * 请求路径：/sms/studentController/getStudentByOpr/{pageNo}/{pageSize}?name=xxx&clazzName=xxx
     *
     * @param pageNo   分页查询的页码
     * @param pageSize 分页查询每页的数据量
     * @param student  分页查询的条件
     * @return
     */
    @ApiOperation("查询学生信息(分页带条件)")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("分页查询的页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询每页的数据量") @PathVariable("pageSize") Integer pageSize,
            // 使用Student对象接收name和clazzName参数
            @ApiParam("分页查询的条件") Student student
    ) {
        Page<Student> page = new Page<>(pageNo, pageSize);
        IPage<Student> studentIPage = studentService.getStudentByOpr(page, student);
        return Result.ok(studentIPage);
    }

    /**
     * 添加或修改学生信息
     *
     * @param student 添加或修改的学生的JSON格式的信息
     * @return
     */
    @ApiOperation("添加或修改学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
            @ApiParam("添加或修改的学生的JSON格式的信息") @RequestBody Student student
    ) {
        // 若id为空，则为添加学生信息，需要将密码转为密文
        Integer id = student.getId();
        if (null == id || 0 == id) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }

        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    /**
     * 删除或批量删除学生信息
     * @param ids 要删除的学生ID的JSON数组
     * @return
     */
    @ApiOperation("删除或批量删除学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(
            @ApiParam("要删除的学生ID的JSON数组") @RequestBody List<Integer> ids
    ) {
        studentService.removeByIds(ids);
        return Result.ok();
    }

}
