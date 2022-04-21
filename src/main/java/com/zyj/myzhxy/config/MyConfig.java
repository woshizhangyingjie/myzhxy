package com.zyj.myzhxy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zhang
 * @Date 2022/4/17 - 18:05
 * @Version 1.0
 */
@Configuration
@MapperScan("com.zyj.myzhxy.mapper")
public class MyConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
