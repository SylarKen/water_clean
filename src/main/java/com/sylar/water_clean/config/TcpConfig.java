package com.sylar.water_clean.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/21 11:01
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
@Data
@PropertySource("classpath:tcpsocket.properties")
public class TcpConfig {
    @Value("${tcpsocket.port}")
    private String SocketPort;

    @Bean(name = "socketPort")
    public String GetSocketPort(){
        return  SocketPort;
    }
}
