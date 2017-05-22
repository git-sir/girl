package com.imooc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ucs_xiaokailin on 2017/5/15.
 */
@Controller
public class HelloController {

    @Value("${cupSize}")
    private String cupSize;

    @Autowired
    private UserProperties userProperties;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String say(){
        String result;
//        result = "Hello Spring Boot";
        result = userProperties.toString();
        return result;
    }

    @RequestMapping(value = "/helloTemplates",method = RequestMethod.GET)
    public String say2(){
        //此处的指定返回的页面文件的名称，会去application.yml文件里spring.thymeleaf.prefix指定的路径下找页面文件
        return "page";
    }
}
