package com.hiooih.core.jwt;

import com.hiooih.base.properties.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author duwenlei
 **/
@Component
public class JwtConfig {

    @Autowired
    private ConfigService configService;

    /**
     * 获取 Token 前缀
     *
     * @return
     */
    public String getTokenPrefix() {
        return configService.getAbdpValue("abdp.jwt.token.prefix", "");
    }

    /**
     * 获取 token header
     *
     * @return
     */
    public String getTokenHeader() {
        return configService.getAbdpValue("abdp.jwt.token.header", "Authorization");
    }

    /**
     * 获取 Token 过期时间
     * 单位 秒
     * @return
     */
    public String getTokenExpire() {
        String tokenExpireStr = configService.getAbdpValue("abdp.jwt.token.expire", "864000s");
        // 时间
        String time = tokenExpireStr.substring(0, tokenExpireStr.length() - 1);

        // 时间单位
        String unit = tokenExpireStr.substring(tokenExpireStr.length() - 1);
        return time;
    }
}
