package com.jinlink;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MiraiStart implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Bot bot = BotFactory.INSTANCE.newBot(2905684658L, BotAuthorization.byPassword("sr2539195984"), botConfiguration -> {
            botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PHONE);
        });

        bot.login();

        new Thread(bot::join).start();
    }
}