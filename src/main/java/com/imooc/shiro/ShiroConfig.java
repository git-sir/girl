package com.imooc.shiro;


import com.imooc.filter.MyFilter;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 * Created by ucs_zhongtingyuan on 2017/3/28.
 */
@Configuration
public class ShiroConfig {
    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

    @Bean(name = "myShiroRealm")
    public AuthorizingRealm getShiroRealm() {
        return new MyShiroRealm();
    }

    @Bean(name = "cacheManager")
    public CacheManager getCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

//    @Bean(name = "csrfTokenRepository")
//    public CsrfTokenRepository getHttpSessionCsrfTokenRepository() {
//        return new HttpSessionCsrfTokenRepository();
//    }
//
//    @Bean(name = "csrfAuthenticationStrategy")
//    public CsrfAuthenticationStrategy getCsrfAuthenticationStrategy() {
//        CsrfAuthenticationStrategy cas = new CsrfAuthenticationStrategy();
//        cas.setCsrfTokenRepository(getHttpSessionCsrfTokenRepository());
//        return cas;
//    }


    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(getShiroRealm());
        //用户授权/认证信息Cache
        dwsm.setCacheManager(getCacheManager());
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(getDefaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

    /**
     * 注册DelegatingFilterProxy（Shiro）
     * 集成Shiro有2种方法：
     * 1. 按这个方法自己组装一个FilterRegistrationBean（这种方法更为灵活，可以自己定义UrlPattern，
     * 在项目使用中你可能会因为一些很但疼的问题最后采用它， 想使用它你可能需要看官网或者已经很了解Shiro的处理原理了）
     * 2. 直接使用ShiroFilterFactoryBean（这种方法比较简单，其内部对ShiroFilter做了组装工作，无法自己定义UrlPattern，
     * 默认拦截 /*）
     */
    @Bean(name = "shiroFilter")//这个bean名称必须为"shiroFilter",因为shiro的Filter类DelegatingFilterProxy会查找这个名称的bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        // 增加Filter
//        shiroFilterFactoryBean.getFilters().put("authc", new MyFormAuthenticationFilter(getHttpSessionCsrfTokenRepository()));
//        shiroFilterFactoryBean.getFilters().put("roles", new MyRolesAuthorizationFilter());
//        shiroFilterFactoryBean.getFilters().put("csrf", new CsrfFilter(getHttpSessionCsrfTokenRepository()));
        // 为日志输出添加ip和用户信息的上下文
//        shiroFilterFactoryBean.getFilters().put("log", new LogFilter());
        shiroFilterFactoryBean.getFilters().put("myFilter", new MyFilter());
        shiroFilterFactoryBean.setLoginUrl("/login");
//        shiroFilterFactoryBean.setSuccessUrl("/successPage");//shiro的SuccessUrl无需配置，没有作用

//        filterChainDefinitionMap.put("/css/**", "anon");
//        filterChainDefinitionMap.put("/images/**", "anon");
//        filterChainDefinitionMap.put("/Javascript/**", "anon");
//        filterChainDefinitionMap.put("/libs/**", "anon");
        filterChainDefinitionMap.put("/shiro-logout", "logout");
//        filterChainDefinitionMap.put("/pages/login/**", "anon");
        //managerPage这个URL用roles过滤器拦截,只有拥有manager这个角色的用户才可以访问
        filterChainDefinitionMap.put("/managerPage", "roles[manager]");
        //该URL用于用户在登录页面输入账号密码后向后台发送请求验证时的URL，所以不能让shiro拦截
        filterChainDefinitionMap.put("/shiroLogin/**", "anon");
//        filterChainDefinitionMap.put("/bindAccount/**", "anon");
//        filterChainDefinitionMap.put("/pages/bind/**", "anon");
//        filterChainDefinitionMap.put("/favicon.ico/**", "anon");

//        filterChainDefinitionMap.put("/**", "log,csrf,authc");
        filterChainDefinitionMap.put("/**", "myFilter,authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

}
