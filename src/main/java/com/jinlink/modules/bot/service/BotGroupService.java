package com.jinlink.modules.bot.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.bot.entity.vo.BotGroupVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.bot.entity.BotGroup;

/**
 * 入驻群管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface BotGroupService extends IService<BotGroup> {
    /**
     * 分页查询入驻群管理。
     */
    RPage<BotGroupVo>  listBotGroupVoPage(PageQuery pageQuery);
}
