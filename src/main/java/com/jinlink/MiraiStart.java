package com.jinlink;

import com.jinlink.modules.mirai.listener.BotEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MiraiStart implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Bot bot = BotFactory.INSTANCE.newBot(2905684658L, BotAuthorization.byQRCode(), botConfiguration -> {
            botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.MACOS);
        });

        bot.login();

        //创建监听器
        GlobalEventChannel.INSTANCE.registerListenerHost(new BotEventHandler());

        new Thread(bot::join).start();
    }
}
