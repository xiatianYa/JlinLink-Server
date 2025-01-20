package com.jinlink.modules.game.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameBindUpdateDTO;
import com.jinlink.modules.game.entity.vo.GameBindVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameBind;
import com.jinlink.modules.game.mapper.GameBindMapper;
import com.jinlink.modules.game.service.GameBindService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏绑键表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameBindServiceImpl extends ServiceImpl<GameBindMapper, GameBind> implements GameBindService {
    @Resource
    private GameBindMapper gameBindMapper;

    /**
     * 分页查询游戏绑键表。
     */
    @Override
    public RPage<GameBindVo> listGameBindVopage(PageQuery pageQuery) {
        Page<GameBind> paginate = gameBindMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper());
        List<GameBind> records = paginate.getRecords();
        List<GameBindVo> gameBindVos = BeanUtil.copyToList(records, GameBindVo.class);
        return RPage.build(new Page<>(gameBindVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }

    /**
     * 根据游戏绑键表社区Id获取详细信息。
     */
    @Override
    public GameBindVo getInfoByCommunityId(Long communityId) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        GameBind gameBind = gameBindMapper.selectOneByQuery(new QueryWrapper()
                .eq("community_id", communityId)
                .eq("create_user_id", loginIdAsLong));
        if (ObjectUtil.isNotNull(gameBind)) {
            return BeanUtil.copyProperties(gameBind, GameBindVo.class);
        }
        return null;
    }

    /**
     * 根据主键更新/新增游戏绑键表。
     */
    @Override
    public Boolean updateGameBind(GameBindUpdateDTO gameBindUpdateDTO) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        GameBind gameBindOne = gameBindMapper.selectOneByQuery(new QueryWrapper()
                .eq("community_id", gameBindUpdateDTO.getCommunityId())
                .eq("create_user_id", loginIdAsLong));
        //覆盖原gameBind 的配置
        if (ObjectUtil.isNull(gameBindOne)) {
            gameBindOne = BeanUtil.copyProperties(gameBindUpdateDTO, GameBind.class);
        }else{
            //修改原属性
            gameBindOne.setCfg(gameBindUpdateDTO.getCfg());
        }
        return gameBindMapper.insertOrUpdate(gameBindOne) >= 0;
    }
}
