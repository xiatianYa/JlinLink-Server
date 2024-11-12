package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameLiveSearchDTO;
import com.jinlink.modules.game.entity.vo.GameLiveVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameLive;
import com.jinlink.modules.game.mapper.GameLiveMapper;
import com.jinlink.modules.game.service.GameLiveService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏直播表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameLiveServiceImpl extends ServiceImpl<GameLiveMapper, GameLive> implements GameLiveService {
    @Resource
    private GameLiveMapper gameLiveMapper;

    /**
     * 分页查询游戏直播表。
     */
    @Override
    public RPage<GameLiveVo> listGameLiveVoPage(PageQuery pageQuery, GameLiveSearchDTO gameLiveSearchDTO) {
        Page<GameLive> paginate = gameLiveMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper()
                .like("uid", gameLiveSearchDTO.getUid()));
        List<GameLive> records = paginate.getRecords();
        List<GameLiveVo> gameLiveVos = BeanUtil.copyToList(records, GameLiveVo.class);
        return RPage.build(new Page<>(gameLiveVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }
}
