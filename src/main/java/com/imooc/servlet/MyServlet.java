package com.imooc.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ucs_xiaokailin on 2017/5/16.
 */
//urlPatterns用于指定servlet所要拦截的url，若不指定则默认值为"/"，即拦截所有url
//不指定name的情况下，name默认值为类全路径，即com.imooc.servlet.MyServlet
@WebServlet(urlPatterns="/myservlet", description="Servlet的说明")
public class MyServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);	//父类的doGet方法会直接调用resp返回页面,所以不能调用父类的方法
        System.out.println(getClass().getSimpleName()+"的doGet方法收到请求");
        resp.setContentType("text/html; charset=utf-8");

        resp.getWriter().write(getClass().getSimpleName()+"的doGet方法返回数据");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);	//父类的doPost方法会直接调用resp返回页面,所以不能调用父类的方法
        System.out.println(getClass().getSimpleName()+"的doPost方法收到请求");
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write(getClass().getSimpleName()+"的doPost方法返回数据");

        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>大家好，我的名字叫Servlet</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(getClass().getSimpleName()+"执行service方法");
        //父类的service会判断当前请求是Get还是Post，然后调用对应的方法去处理
        super.service(req, resp);
    }
}
