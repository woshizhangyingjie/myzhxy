package com.zyj.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyj.myzhxy.pojo.Grade;
import com.zyj.myzhxy.service.GradeService;
import com.zyj.myzhxy.util.Result;
import io.swagger.annotations.Api;
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
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 获取年级信息(分页带条件)
     * 请求路径：localhost:8080/sms/gradeController/getGrades/{pageNo}/{pageSize}?gradeName=xxx
     * 其中，请求路径的gradeName为密文
     *
     * @param pageNo    分页查询的页码数
     * @param pageSize  分页查询每页的数据数
     * @param gradeName 年级名字，用于搜索时的模糊查询
     * @return
     */
    @ApiOperation("获取年级信息(分页带条件)")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询每页的数据数") @PathVariable("pageSize") Integer pageSize,
            // @RequestParam可以省略，若不省略，必须加上required = false，否则会出错
            @ApiParam("年级名字，用于搜索时的模糊查询") @RequestParam(value = "gradeName", required = false) String gradeName
    ) {
        // 分页，带条件查询
        Page<Grade> page = new Page<>(pageNo, pageSize);

        // 获取年级信息
        IPage<Grade> pageRs = gradeService.getGradesByOpr(page, gradeName);

        // 封装Result对象并返回
        return Result.ok(pageRs);
    }

    /**
     * 添加或修改年级信息，参数grade有id属性为修改，没有是添加
     * @param grade 年级信息的JSON对象
     * @return
     */
    @ApiOperation("添加或修改年级信息，参数grade有id属性为修改，没有是添加")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdate(
            // 从请求体中接收参数
            @ApiParam("年级信息的JSON对象") @RequestBody Grade grade
    ) {
        gradeService.saveOrUpdate(grade);

        return Result.ok();
    }

    /**
     * 删除或批量删除年级信息
     * @param ids 要删除的年级信息的id的JSON集合
     * @return
     */
    @ApiOperation("(批量)删除年级信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            // 从请求体中接收参数json数组
            @ApiParam("要删除的年级信息的id的JSON集合") @RequestBody List<Integer> ids
    ) {
        gradeService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 获取全部年级
     * @return
     */
    @ApiOperation("获取全部年级")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> gradeList = gradeService.list();
        return Result.ok(gradeList);
    }

}
