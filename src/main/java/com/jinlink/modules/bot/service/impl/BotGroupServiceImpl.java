package com.jinlink.modules.bot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.bot.entity.vo.BotGroupVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.bot.entity.BotGroup;
import com.jinlink.modules.bot.mapper.BotGroupMapper;
import com.jinlink.modules.bot.service.BotGroupService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 入驻群管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class BotGroupServiceImpl extends ServiceImpl<BotGroupMapper, BotGroup> implements BotGroupService {
    @Resource
    private BotGroupMapper botGroupMapper;

    @Override
    public RPage<BotGroupVo> listBotGroupVoPage(PageQuery pageQuery) {
        Page<BotGroup> paginate = botGroupMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper());
        List<BotGroup> records = paginate.getRecords();
        List<BotGroupVo> botGroupVos = BeanUtil.copyToList(records, BotGroupVo.class);
        return RPage.build(new Page<>(botGroupVos,pageQuery.getCurrent(),pageQuery.getSize(),paginate.getTotalRow()));
    }
}
