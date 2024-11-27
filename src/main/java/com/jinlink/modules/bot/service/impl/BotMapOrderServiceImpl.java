package com.jinlink.modules.bot.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.bot.entity.BotMapOrder;
import com.jinlink.modules.bot.mapper.BotMapOrderMapper;
import com.jinlink.modules.bot.service.BotMapOrderService;
import org.springframework.stereotype.Service;

/**
 * 地图订阅表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class BotMapOrderServiceImpl extends ServiceImpl<BotMapOrderMapper, BotMapOrder> implements BotMapOrderService {

}
