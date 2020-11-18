package com.alex.logback.demo.controller;

import com.alex.logback.demo.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        try{
            int a = 10/0;
        }catch (Exception e) {
            logger.error("hello",e);
        }


        return indexService.doService("name");
    }
}
