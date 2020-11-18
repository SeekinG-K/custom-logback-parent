package com.alex.logback.demo.service.impl;

import com.alex.logback.demo.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {

    private static final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Override
    public String doService(String name) {
        logger.info("Service: "+ name);
        logger.warn("Service: "+ name);
        logger.debug("Service: "+ name);
        logger.error("Service: "+ name);
        return name;
    }
}
