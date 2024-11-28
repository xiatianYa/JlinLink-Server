package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.domain.Options;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.entity.vo.GameCommunityVo;
import com.jinlink.modules.game.mapper.GameServerMapper;
import com.jinlink.modules.game.service.GameServerService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.mapper.GameCommunityMapper;
import com.jinlink.modules.game.service.GameCommunityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏社区表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameCommunityServiceImpl extends ServiceImpl<GameCommunityMapper, GameCommunity> implements GameCommunityService {
    @Resource
    private GameCommunityMapper gameCommunityMapper;
    @Resource
    private GameServerMapper gameServerMapper;

    /**
     * 分页查询游戏社区表。
     */
    @Override
    public RPage<GameCommunityVo> listGameCommunityVoPage(PageQuery pageQuery) {
        Page<GameCommunity> paginate = gameCommunityMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper());
        List<GameCommunity> records = paginate.getRecords();
        List<GameCommunityVo> gameCommunityVos = BeanUtil.copyToList(records, GameCommunityVo.class);
        return RPage.build(new Page<>(gameCommunityVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }

    /**
     * 查询全部社区名称。
     */
    @Override
    public List<Options<String>> getAllCommunityNames() {
        List<GameCommunity> gameCommunities = gameCommunityMapper.selectAll();
        return gameCommunities.stream()
                .map(item -> Options.<String>builder()
                        .label(item.getCommunityName())
                        .value(String.valueOf(item.getId()))
                        .build())
                .toList();
    }

    @Override
    public Boolean removeCommunityById(Serializable id) {
        //查询当前社区下有没有服务器 如果有则不允许删除
        List<GameServer> gameServers = gameServerMapper.selectListByQuery(new QueryWrapper().eq("community_id", id));
        if (ObjectUtil.isNotNull(gameServers)){
            throw new JinLinkException("当前社区下还有服务器,不允许删除");
        }
        return removeById(id);
    }

    /**
     * 查询全部社区名称。
     */
    @Override
    public List<String> getCommunityNames() {
        List<GameCommunity> gameCommunities = gameCommunityMapper.selectAll();
        return gameCommunities.stream().map(GameCommunity::getCommunityName).toList();
    }
}
