package com.jinlink;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class JinLinkServerApplicationTests {

    @Test
    void contextLoads() {
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        // 解析给定的日期时间字符串
        String lastRunStr = "2024-10-21T17:53:42";
        LocalDateTime lastRun = LocalDateTime.parse(lastRunStr, formatter);

        // 定义要加的小时数
        long hoursToAdd = 2400;

        // 计算加上小时数后的日期时间
        Duration duration = Duration.ofMinutes(hoursToAdd);
        LocalDateTime newTime = lastRun.plus(duration);

        // 获取当前时间（考虑时区，这里使用UTC作为示例）
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        LocalDateTime currentTime = now.toLocalDateTime();

        // 打印结果
        System.out.println("加上40个小时后的时间是: " + newTime);
        System.out.println("当前时间是: " + currentTime);

        // 判断加上40个小时后的时间是否小于当前时间
        boolean isBefore = newTime.isBefore(currentTime);
        System.out.println("加上40个小时后的时间是否小于当前时间: " + isBefore);
    }

}
