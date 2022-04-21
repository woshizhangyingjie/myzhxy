package com.zyj.myzhxy.service;

import com.zyj.myzhxy.pojo.Clazz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ClazzService extends IService<Clazz> {

    /**
     * 查询班级信息(分页带条件)
     * @param page
     * @param clazz 分页查询的条件
     * @return
     */
    IPage<Clazz> getClazzByOpr(Page<Clazz> page, Clazz clazz);

}
