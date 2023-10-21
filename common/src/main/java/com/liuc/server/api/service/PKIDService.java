package com.liuc.server.api.service;

import com.liuc.server.api.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * created by lijh
 *  2019-06-21
 */
@Component
@Slf4j
public class PKIDService {


    @Value("${server.datacenterId}")
    private long datacenterId;
    @Value("${server.workId}")
    private long workId;

    private static volatile SnowflakeIdWorker instance;

    public SnowflakeIdWorker getInstance() {
        if (instance == null) {
            synchronized (SnowflakeIdWorker.class) {
                if (instance == null) {
                    log.info("when instance, workId = {}, datacenterId = {}", workId, datacenterId);
                    instance = new SnowflakeIdWorker(workId, datacenterId);
                }
            }
        }
        return instance;
    }

}
