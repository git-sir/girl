package com.imooc.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ucs_xiaokailin on 2017/5/17.
 */
@Controller
public class ShiroController {

    private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);
    @RequestMapping(value = "login")
    public String login (HttpServletRequest httpRequest, Model model) {
        logger.info(getClass().getSimpleName()+"拦截到"+httpRequest.getRequestURI());
        if(SecurityUtils.getSubject().isAuthenticated()){
            return "redirect:index";
        }
        /*
            当前这个类是一个用@Controller注解的类，当前方法是用@RequestMapping注解的方法。所以这里方法的返回值，springboot
            会去查找以该值命名的模板文件.模板文件所在路径已在项目的application.yml文件里spring.thymeleaf.prefix中指定
         */
        return "loginPage";
    }

    @RequestMapping(value = "shiroLogin")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        logger.info(getClass().getSimpleName()+"拦截到/girl/shiroLogin");
        if(ShiroUtil.login(username,password)){
            logger.info(username+"登陆成功");
            return "redirect:index";
        }
        return "loginPage";
    }

    @RequestMapping("logout")
    public String logout(){

        ShiroUtil.logoutCurUser();
        return "redirect:loginPage";
    }
    //这个URL用于：在用户已登录的情况下，再次访问登录页面时重定向到成功页面
    @RequestMapping("index")
    public String index(){
        return "successPage";
    }

    private void generatePwd(){
        String saltSource = "abcd";		//盐值

        String hashAlgorithmName = "MD5";	//使用MD5算法
        String credentials = "123456";		//需要加密的数据
        Object salt = new Md5Hash(saltSource);
        int hashIterations = 1024;		//迭代加密的次数

        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        logger.info("加密后的数据："+result.toString());
    }

//    @RequiresRoles("manager")//@RequiresRoles用于指定可以访问managerPage这个URL的角色,也可以在ShiroConfig中配置过滤器来实现
    @RequestMapping("managerPage")
    public String managerPage(){
        logger.info("访问managerPage");
		/*
		 * 注：这里的"managerPage"页面没有指定访问权限的话,则用户登录后便可以直接打开
		 */
        return "managerPage";
    }
}
