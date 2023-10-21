package com.liuc.server.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.liuc.server.api.mapper", "com.liuc.server.api.common"})
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

    @Bean(name = "crawlExecutorPool")
    public ExecutorService crawlExecutorPool() {
        ExecutorService pool = Executors.newFixedThreadPool(20);
        return pool;
    }

}
