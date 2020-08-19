package com.hiooih.base.auth;

import com.hiooih.base.auth.filter.JwtAuthenticationTokenFilter;
import com.hiooih.base.auth.handler.UserAccessDeniedHandler;
import com.hiooih.base.auth.handler.UserAuthenticationEntryPointHandler;
import com.hiooih.base.auth.handler.UserAuthenticationFailureHandler;
import com.hiooih.base.auth.handler.UserAuthenticationSuccessHandler;
import com.hiooih.base.auth.handler.UserLogoutSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;

/**
 * @author duwenlei
 **/
@Configuration
@Slf4j
public class UserWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    /**
     * 登录成功
     */
    @Autowired
    private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;

    /**
     * 登陆失败
     */
    @Autowired
    private UserAuthenticationFailureHandler userAuthenticationFailureHandler;

    /**
     * 退出
     */
    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    /**
     * 无权限
     */
    @Autowired
    private UserAccessDeniedHandler userAccessDeniedHandler;

    /**
     * 未登录
     */
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;


    /**
     * 自定义登录
     */
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("加载 自定义登录验证 提供者");
        auth.authenticationProvider(userAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 不进行验证的请求地址
                .antMatchers("/user/login", "/system/sysUser/save", "/abdp/system/sysUser/save").permitAll()
                .accessDecisionManager(accessDecisionManager())
                // 其他的请求地址，需要登录才能访问
                .anyRequest().authenticated()
                .and()
                // 未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                .formLogin()
                .loginProcessingUrl("/user/login")
                .successHandler(userAuthenticationSuccessHandler)
                .failureHandler(userAuthenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(userAccessDeniedHandler)
                .and()
                .cors()
                .and()
                .csrf()
                // 取消跨站请求伪造防护
                .disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().cacheControl();
        http.addFilter(new JwtAuthenticationTokenFilter(authenticationManager()));
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new UnanimousBased(
                Arrays.asList(
                        new WebExpressionVoter(),
                        new UserAccessDecisionVoter(),
                        new AuthenticatedVoter()
                )
        );
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DefaultWebSecurityExpressionHandler userDefaultWebSecurityExpressionHandler() {
        log.info("加载自定义权限注解验证器。");
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setPermissionEvaluator(new UserPermissionEvaluator());
        return defaultWebSecurityExpressionHandler;
    }
}
