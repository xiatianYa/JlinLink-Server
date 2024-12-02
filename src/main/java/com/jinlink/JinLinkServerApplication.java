package com.jinlink;

import com.jinlink.common.util.mcping.MinecraftPing;
import com.jinlink.common.util.mcping.MinecraftPingOptions;
import com.jinlink.common.util.mcping.MinecraftPingReply;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;

@EnableCaching
@SpringBootApplication
public class JinLinkServerApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(JinLinkServerApplication.class, args);
        //MinecraftPingReply data = new MinecraftPing().getPing(new MinecraftPingOptions().setHostname("mc.hypixel.net").setPort(25565));
    }
}
