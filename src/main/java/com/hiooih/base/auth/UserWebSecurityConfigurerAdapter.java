package com.hiooih.base.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author duwenlei
 **/
@Configuration
public class UserWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .hasRole("user")
                .and();
    }

    public static class SecurityUserInit {
        @Bean
        public UserDetailsService userDetailsService() {
            InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
            inMemoryUserDetailsManager.createUser(
                    User.withUsername("user")
                            .password("123")
                            .roles("user")
                            .build()
            );
            inMemoryUserDetailsManager.createUser(
                    User.withUsername("guest")
                            .password("123")
                            .roles("guest")
                            .build()
            );
            return inMemoryUserDetailsManager;
        }
    }
}
