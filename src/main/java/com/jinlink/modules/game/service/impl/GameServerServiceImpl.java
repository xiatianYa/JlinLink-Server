package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.mapper.GameServerMapper;
import com.jinlink.modules.game.service.GameServerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏服务器表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameServerServiceImpl extends ServiceImpl<GameServerMapper, GameServer> implements GameServerService {
    @Resource
    private GameServerMapper gameServerMapper;

    /**
     * 分页查询游戏服务器表。
     */
    @Override
    public RPage<GameServerVo> listGameServerVoPage(PageQuery pageQuery) {
        Page<GameServer> paginate = gameServerMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper());
        List<GameServer> records = paginate.getRecords();
        List<GameServerVo> gameServerVos = BeanUtil.copyToList(records, GameServerVo.class);
        return RPage.build(new Page<>(gameServerVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }
}
