package com.zyj.myzhxy.service.impl;

import com.zyj.myzhxy.mapper.ClazzMapper;
import com.zyj.myzhxy.pojo.Clazz;
import com.zyj.myzhxy.service.ClazzService;
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
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    /**
     * 查询班级信息(分页带条件)
     * @param page
     * @param clazz 分页查询的条件
     * @return
     */
    @Override
    public IPage<Clazz> getClazzByOpr(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        // 若分页的查询条件不为空，则添加条件
        String gradeName = clazz.getGradeName();
        if(!StringUtils.isEmpty(gradeName)){
            queryWrapper.eq("grade_name", gradeName);
        }
        String name = clazz.getName();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name", name);
        }
        // 根据id降序排序
        queryWrapper.orderByDesc("id");
        Page<Clazz> clazzPage = baseMapper.selectPage(page, queryWrapper);
        return clazzPage;
    }

}
