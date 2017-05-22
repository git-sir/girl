package com.imooc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by ucs_xiaokailin on 2017/5/16.
 */
@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println(getClass().getSimpleName()+"执行contextInitialized方法");
        String sourceName = servletContextEvent.getSource().getClass().getSimpleName();
        System.out.println("触发此监听器的事件源 : "+sourceName);

        //获取web.xml中在context-param标签配置的信息
//        ServletContext servletContext = servletContextEvent.getServletContext();
//        String paramName = "contextConfigLocation";
//        String paramValue = servletContext.getInitParameter(paramName);
//        System.out.println("读取web.xml中在context-param标签配置的值 : "+paramValue);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println(getClass().getSimpleName()+"执行contextDestroyed方法");
    }
}
