package com.jinlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class JinLinkServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JinLinkServerApplication.class, args);
    }
}
