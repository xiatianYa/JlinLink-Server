package com.jinlink;

import com.jinlink.common.util.AgqlUtils;
import com.jinlink.common.util.mcping.MinecraftPing;
import com.jinlink.common.util.mcping.MinecraftPingOptions;
import com.jinlink.common.util.mcping.MinecraftPingReply;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@EnableCaching
@SpringBootApplication
public class JinLinkServerApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(JinLinkServerApplication.class, args);
//        long startTime = System.currentTimeMillis();
//        // 创建一个固定大小的线程池
//        ExecutorService executor = Executors.newFixedThreadPool(4);
//        // 存储Future对象的列表，以便稍后获取结果
//        List<Future<MinecraftPingReply>> futures = new ArrayList<>();
//        for (int i = 0; i <= 10; i++) {
//            Callable<MinecraftPingReply> callable = () -> new MinecraftPing().getPing(new MinecraftPingOptions().setHostname("mc.hypixel.net").setPort(25565));
//            Future<MinecraftPingReply> future = executor.submit(callable);
//            futures.add(future);
//        }
//        // 等待所有任务完成并收集结果
//        List<MinecraftPingReply> serverVos = new ArrayList<>();
//        for (Future<MinecraftPingReply> future : futures) {
//            MinecraftPingReply minecraftPingReply;
//            try {
//                minecraftPingReply = future.get();
//                serverVos.add(minecraftPingReply);
//            } catch (InterruptedException | ExecutionException e) {
//                System.out.println("社区数据获取失败!");
//                return;
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        long duration = endTime - startTime;
//
//        System.out.println("任务执行时间: " + duration + " 毫秒");
    }
}
