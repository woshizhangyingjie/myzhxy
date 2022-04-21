package com.zyj.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyj.myzhxy.pojo.Clazz;
import com.zyj.myzhxy.service.ClazzService;
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
@Api(tags = "班级管理器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    /**
     * 查询班级信息(分页带条件)
     * 请求路径为：/sms/clazzController/getClazzsByOpr/{pageNo}/{pageSize}?gradeName=xxx&name=xxx
     * @param pageNo 分页查询的页码
     * @param pageSize 分页查询每页的数据量
     * @param clazz 分页查询的查询条件
     * @return
     */
    @ApiOperation("查询班级信息(分页带条件)")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzByOpr(
            @ApiParam("分页查询的页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询每页的数据量") @PathVariable("pageSize") Integer pageSize,
            // 使用Clazz对象来接收gradeName和name
            @ApiParam("分页查询的查询条件") Clazz clazz
    ){
        Page<Clazz> page = new Page<>(pageNo, pageSize);
        IPage<Clazz> clazzIPage = clazzService.getClazzByOpr(page, clazz);
        return Result.ok(clazzIPage);
    }

    /**
     * 添加或修改班级信息
     * @param clazz JSON格式的班级信息
     * @return
     */
    @ApiOperation("添加或修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
            @ApiParam("JSON格式的班级信息") @RequestBody Clazz clazz
    ){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    /**
     * 删除或批量删除班级信息
     * @param ids 要删除的多个班级的ID的JSON数组
     * @return
     */
    @ApiOperation("删除或批量删除班级信息")
    @DeleteMapping("deleteClazz")
    public Result deleteClazz(
            @ApiParam("要删除的多个班级的ID的JSON数组") @RequestBody List<Integer> ids
    ){
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 查询所有班级信息
     * @return
     */
    @ApiOperation("查询所有班级信息")
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzList = clazzService.list();
        return Result.ok(clazzList);
    }

}
