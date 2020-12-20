package com.alex.logback.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/provider")
public class ProviderController {


    @ResponseBody
    @RequestMapping("/providerData")
    public String provider() {
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        log.info("aaa");
        return "this is provider!";
    }

    @Value("${app.version}")
    private String version;

    @Value("${app.build.time}")
    private String buildTime;

    @GetMapping("/version")
    @ResponseBody
    public Map<String,String> uploadImg() {
        Map<String,String> ret = new HashMap<>();
        ret.put("version",version);
        ret.put("buildTime",buildTime);
        return ret;
    }

}
