package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameCommunityVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.mapper.GameCommunityMapper;
import com.jinlink.modules.game.service.GameCommunityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
}
