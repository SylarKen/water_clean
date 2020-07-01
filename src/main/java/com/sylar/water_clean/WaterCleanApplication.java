package com.sylar.water_clean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WaterCleanApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WaterCleanApplication.class, args);
    }

}
