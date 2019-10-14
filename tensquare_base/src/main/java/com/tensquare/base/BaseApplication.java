package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import util.IdWorker;


// 项目入口
@SpringBootApplication
//@CrossOrigin  // 跨域问题
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    // ID生成器
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
