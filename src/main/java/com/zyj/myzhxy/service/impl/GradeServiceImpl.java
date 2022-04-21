package com.zyj.myzhxy.service.impl;

import com.zyj.myzhxy.mapper.GradeMapper;
import com.zyj.myzhxy.pojo.Grade;
import com.zyj.myzhxy.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    /**
     * 获取年级信息(分页带条件)
     * @param page
     * @param gradeName 年级关键字搜索，用于模糊查询
     * @return
     */
    @Override
    public IPage<Grade> getGradesByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(gradeName)){
            // gradeName不为空，添加模糊查询条件
            queryWrapper.like("name", gradeName);
        }
        queryWrapper.orderByDesc("id"); // 根据id倒序排列

        Page<Grade> gradePage = baseMapper.selectPage(page, queryWrapper);

        return gradePage;
    }

}
