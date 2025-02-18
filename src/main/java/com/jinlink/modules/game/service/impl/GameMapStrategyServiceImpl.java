package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameMapStrategyVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameMapStrategy;
import com.jinlink.modules.game.mapper.GameMapStrategyMapper;
import com.jinlink.modules.game.service.GameMapStrategyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地图攻略表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameMapStrategyServiceImpl extends ServiceImpl<GameMapStrategyMapper, GameMapStrategy> implements GameMapStrategyService {

    @Resource
    private GameMapStrategyMapper gameMapStrategyMapper;

    /**
     * 分页查询地图攻略表。
     */
    @Override
    public RPage<GameMapStrategyVo> listGameMapStrategyVoPage(PageQuery pageQuery) {
        Page<GameMapStrategy> paginate = gameMapStrategyMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper());
        List<GameMapStrategy> records = paginate.getRecords();
        List<GameMapStrategyVo> gameMapStrategyVos = BeanUtil.copyToList(records, GameMapStrategyVo.class);
        return RPage.build(new Page<>(gameMapStrategyVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }
}
