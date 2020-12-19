package com.alex.logback.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Summerday
 */
@Component
@FeignClient(value = "custom-logback-provider")
public interface ProviderFeignClient {

    @GetMapping("/payment/{id}")
    String getById(@PathVariable("id") Long id);

    @GetMapping("/payment/timeOut")
    String paymentTimeOut();

    @RequestMapping("/provider")
    @ResponseBody
    public String provider();
}
