package com.hiooih;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.security.Security;

/**
 * @author duwenlei
 */
@SpringBootApplication
@MapperScan("com.hiooih")
public class AbdpApplication {

    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        applicationContext = SpringApplication.run(AbdpApplication.class, args);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static Object getBean(String beanName, Class requiredType) {
        return applicationContext.getBean(beanName, requiredType);
    }
}
