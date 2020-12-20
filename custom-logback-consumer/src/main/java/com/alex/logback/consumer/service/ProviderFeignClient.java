package com.alex.logback.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "custom-logback-provider")
public interface ProviderFeignClient {

    @RequestMapping("/provider/providerData")
    @ResponseBody
    public String provider();
}
