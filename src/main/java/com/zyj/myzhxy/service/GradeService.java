package com.zyj.myzhxy.service;

import com.zyj.myzhxy.pojo.Grade;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GradeService extends IService<Grade> {

    /**
     * 获取年级信息(分页带条件)
     * @param page
     * @param gradeName 年级关键字搜索，用于模糊查询
     * @return
     */
    IPage<Grade> getGradesByOpr(Page<Grade> page, String gradeName);

}
