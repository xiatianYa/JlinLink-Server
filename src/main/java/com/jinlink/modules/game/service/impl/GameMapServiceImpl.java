package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.jinlink.common.domain.BTPairs;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameMapAddDTO;
import com.jinlink.modules.game.entity.dto.GameMapSearchDTO;
import com.jinlink.modules.game.entity.dto.GameMapUpdateDTO;
import com.jinlink.modules.game.entity.vo.GameMapVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameMap;
import com.jinlink.modules.game.mapper.GameMapMapper;
import com.jinlink.modules.game.service.GameMapService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏地图表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameMapServiceImpl extends ServiceImpl<GameMapMapper, GameMap> implements GameMapService {
    @Resource
    private GameMapMapper gameMapMapper;

    @Override
    public RPage<GameMapVo> listGameMapVoPage(PageQuery pageQuery,GameMapSearchDTO gameMapSearchDTO) {
        Page<GameMap> paginate = gameMapMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper()
                .like("map_name", gameMapSearchDTO.getMapName())
                .like("map_label", gameMapSearchDTO.getMapLabel())
                .eq("type", gameMapSearchDTO.getType())
                .eq("mode_id", gameMapSearchDTO.getModeId()));
        List<GameMap> records = paginate.getRecords();
        List<GameMapVo> gameMapVos = new ArrayList<>();
        records.forEach(item->{
            GameMapVo gameMapVo = BeanUtil.copyProperties(item, GameMapVo.class, "artifact","tag");
            gameMapVo.setTag(JSON.parseArray(item.getTag(),String.class));
            gameMapVo.setArtifact(JSON.parseArray(item.getArtifact(),BTPairs.class));
            gameMapVos.add(gameMapVo);
        });
        return RPage.build(new Page<>(gameMapVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }

    /**
     * 添加游戏地图表。
     */
    @Override
    public Boolean saveGameMap(GameMapAddDTO gameMapAddDTO) {
        GameMap gameMap = BeanUtil.copyProperties(gameMapAddDTO, GameMap.class);
        String artifact = JSON.toJSONString(gameMapAddDTO.getArtifact());
        gameMap.setArtifact(artifact);
        //查询当前地图存不存在
        GameMap mapName = gameMapMapper.selectOneByQuery(new QueryWrapper().eq("map_name", gameMapAddDTO.getMapName()));
        if (ObjectUtil.isNotNull(mapName)) {
            throw new JinLinkException("当前地图已存在!");
        }
        if (ObjectUtil.isNull(gameMap)){
            throw new JinLinkException("非法参数!");
        }
        return save(gameMap);
    }

    /**
     * 根据主键更新游戏地图表。
     */
    @Override
    public Boolean updateGameMap(GameMapUpdateDTO gameMapUpdateDTO) {
        GameMap gameMap = BeanUtil.copyProperties(gameMapUpdateDTO, GameMap.class);
        String artifact = JSON.toJSONString(gameMapUpdateDTO.getArtifact());
        gameMap.setArtifact(artifact);
        if (ObjectUtil.isNull(gameMap)){
            throw new JinLinkException("非法参数!");
        }
        return updateById(gameMap);
    }
}
