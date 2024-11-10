package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.domain.Options;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.GameGame;
import com.jinlink.modules.game.entity.vo.GameModeVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameMode;
import com.jinlink.modules.game.mapper.GameModeMapper;
import com.jinlink.modules.game.service.GameModeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏模式表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameModeServiceImpl extends ServiceImpl<GameModeMapper, GameMode> implements GameModeService {
    @Resource
    private GameModeMapper gameModeMapper;

    /**
     * 分页查询游戏模式表。
     */
    @Override
    public RPage<GameModeVo> listGameModeVoPage(PageQuery pageQuery) {
        Page<GameMode> paginate = gameModeMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper());
        List<GameMode> records = paginate.getRecords();
        List<GameModeVo> gameModeVos = BeanUtil.copyToList(records, GameModeVo.class);
        return RPage.build(new Page<>(gameModeVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }

    /**
     * 查询全部模式名称。
     */
    @Override
    public List<Options<String>> allModeNames() {
        List<GameMode> gameModes = gameModeMapper.selectAll();
        return gameModes.stream()
                .map(item -> Options.<String>builder()
                        .label(item.getModeName())
                        .value(String.valueOf(item.getId()))
                        .build())
                .toList();
    }
}
