package com.alex.logback.consumer.controller;

import com.alex.logback.consumer.service.ProviderFeignClient;
import lombok.extern.slf4j.Slf4j;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Slf4j
public class ConsumerController {

    @Autowired
    private ProviderFeignClient providerFeignClient;

    @RequestMapping("/consumer")
    @ResponseBody
    public String consumer() {
        String provider = providerFeignClient.provider();
        log.info(provider);
        Map<String, Object> map = MDC.getMap();
        return "this is consumer!";
    }
}
