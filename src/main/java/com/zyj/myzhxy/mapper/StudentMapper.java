package com.zyj.myzhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyj.myzhxy.pojo.Clazz;
import com.zyj.myzhxy.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author zhang
 * @Date 2022/4/18 - 16:04
 * @Version 1.0
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
