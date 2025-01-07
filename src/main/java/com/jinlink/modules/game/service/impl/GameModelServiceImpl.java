package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameModelVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameModel;
import com.jinlink.modules.game.mapper.GameModelMapper;
import com.jinlink.modules.game.service.GameModelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏模型表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameModelServiceImpl extends ServiceImpl<GameModelMapper, GameModel> implements GameModelService {
    @Resource
    private GameModelMapper gameModelMapper;

    /**
     * 分页查询游戏模型表。
     */
    @Override
    public RPage<GameModelVo> listGameModelVoPage(PageQuery pageQuery) {
        Page<GameModel> paginate = gameModelMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper());
        List<GameModel> records = paginate.getRecords();
        List<GameModelVo> gameModeVos = BeanUtil.copyToList(records, GameModelVo.class);
        return RPage.build(new Page<>(gameModeVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }
}
