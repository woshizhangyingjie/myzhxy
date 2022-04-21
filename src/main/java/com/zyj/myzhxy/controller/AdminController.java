package com.zyj.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyj.myzhxy.pojo.Admin;
import com.zyj.myzhxy.service.AdminService;
import com.zyj.myzhxy.util.MD5;
import com.zyj.myzhxy.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/4/18 - 16:11
 * @Version 1.0
 */
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 查询管理员信息(分页带条件)
     * @param pageNo 分页查询的页码数
     * @param pageSize 分页查询每页的数据量
     * @param adminName 管理员名字,用于模糊查询
     * @return
     */
    @ApiOperation("查询管理员信息(分页带条件)")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询每页的数据量") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("管理员名字,用于模糊查询") String adminName
    ){
        Page<Admin> page = new Page<>(pageNo, pageSize);
        IPage<Admin> iPage = adminService.getAdminByOpr(page, adminName);
        return Result.ok(iPage);
    }

    /**
     * 添加或修改管理员信息
     * @param admin 添加或修改的管理员信息
     * @return
     */
    @ApiOperation("添加或修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @ApiParam("添加或修改的管理员信息") @RequestBody Admin admin
    ){
        Integer id = admin.getId();
        if(null == id || id == 0){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    /**
     * 删除或批量删除管理员信息
     * @param ids 要删除的管理员的id的JSON数组
     * @return
     */
    @ApiOperation("删除或批量删除管理员信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
            @ApiParam("要删除的管理员的id的JSON数组") @RequestBody List<Integer> ids
    ){
        adminService.removeByIds(ids);
        return Result.ok();
    }

}
