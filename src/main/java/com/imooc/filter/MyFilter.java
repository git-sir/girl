package com.imooc.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by ucs_xiaokailin on 2017/5/16.
 */
@WebFilter(urlPatterns = "/myfilter",
        initParams = {@WebInitParam(name= "encoding", value="UTF-8"),@WebInitParam(name= "key2", value="value2")})
public class MyFilter implements Filter {

    private String className = getClass().getSimpleName();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(className+"执行init方法");
        //读取出MyFilter的所有initParams参数，此参数在@WebFilter注解上配置
        Enumeration<String> list = filterConfig.getInitParameterNames();
        System.out.println("读取"+className+"在@WebFilter注解里配置的initParams");
        while (list.hasMoreElements()){
            String paramName = list.nextElement();
            String paramValue = filterConfig.getInitParameter(paramName);
            System.out.println(paramName+" : "+paramValue);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(className+"的doFilter方法拦截到请求");
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        String url = httpServletRequest.getRequestURI();
        System.out.println(className+"拦截到的url为:"+url);
		/*
		 * 在doFilter中处理完成业务之后，最后要记得调用处理链FilterChain的doFilter方法，把请求传递给下一个过滤器。
		 * 直至最后传递给servlet去处理。否则的话，请求就会截止到当前这个Filter，也就不会传递给servlet处理了。
		 */
        filterChain.doFilter(servletRequest, servletResponse);

        //若不想继续传递给下一个过滤器，也可以直接返回数据到前端
//        servletResponse.setContentType("text/html; charset=utf-8");
//        PrintWriter out = servletResponse.getWriter();
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<title>Hello MyFilter</title>");
//        out.println("</head>");
//        out.println("<body>");
//        out.println("<h1>大家好，MyFilter</h1>");
//        out.println("</body>");
//        out.println("</html>");
    }

    @Override
    public void destroy() {
        System.out.println(className+"执行destroy方法");
    }
}
