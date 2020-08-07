package com.hiooih.base.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;


/**
 * @author duwenlei
 **/
@Component
@Slf4j
public class ConfigService {

    @Autowired
    private Environment environment;
    private Properties properties;


    public String getValue(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    public String getAbdpConfigPath() {
        return getValue("abdp.config.file", "config/abdp.properties");
    }

    private void initAbdpConfigEnv(String configFile) {
        try {
            if (properties == null) {
                properties = PropertiesLoaderUtils.loadAllProperties(configFile);
            }
        } catch (IOException e) {
            log.error("读取配置文件错误，[abdp.config.file={}]，原因：{}", configFile, e.getMessage(), e);
        }
    }

    /**
     * 读取项目自定义配置函数
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return 值
     */
    public String getAbdpValue(String key, String defaultValue) {
        if (properties == null) {
            initAbdpConfigEnv(getAbdpConfigPath());
        }
        return properties.getProperty(key, defaultValue);
    }

}
