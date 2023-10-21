package com.liuc.server.api;


import com.liuc.server.api.common.C;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * Created by jclang on 2017/5/20.
 */
@Component
@Slf4j
public class StartupRunner implements ApplicationRunner {

    @Autowired
    private Environment env;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 程序启动时执行
        if ("dev".equals(env.getProperty("profile")) || "loc".equals(env.getProperty("profile"))) {
            C.DEBUG = true;
        } else {
            C.DEBUG = false;
        }
        C.isInit = true;
    }
}
