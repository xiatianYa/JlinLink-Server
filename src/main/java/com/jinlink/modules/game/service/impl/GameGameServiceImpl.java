package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameGameVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameGame;
import com.jinlink.modules.game.mapper.GameGameMapper;
import com.jinlink.modules.game.service.GameGameService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameGameServiceImpl extends ServiceImpl<GameGameMapper, GameGame> implements GameGameService {
    @Resource
    private GameGameMapper gameGameMapper;

    /**
     * 分页查询游戏表。
     */
    @Override
    public RPage<GameGameVo> listGameGameVoPage(PageQuery pageQuery) {
        Page<GameGame> paginate = gameGameMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper());
        List<GameGame> records = paginate.getRecords();
        List<GameGameVo> gameGameVos = BeanUtil.copyToList(records, GameGameVo.class);
        return RPage.build(new Page<>(gameGameVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }
}
