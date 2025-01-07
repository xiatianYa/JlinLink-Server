package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.jinlink.common.domain.BTPairs;
import com.jinlink.common.domain.Options;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.util.AgqlUtils;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.ExgMapDTO;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        List<ExgMapDTO> exgMaps = AgqlUtils.getExgMapApiServer();
        List<GameMap> records = paginate.getRecords();
        List<GameMapVo> gameMapVos = new ArrayList<>();
        records.forEach(item->{
            //查询EXG地图CD
            Optional<ExgMapDTO> exgResult = Objects.requireNonNull(exgMaps).stream().filter(map -> map.getName().equals(item.getMapName())).findFirst();
            GameMapVo gameMapVo = BeanUtil.copyProperties(item, GameMapVo.class, "artifact","tag");
            gameMapVo.setTag(JSON.parseArray(item.getTag(),String.class));
            gameMapVo.setArtifact(JSON.parseArray(item.getArtifact(),BTPairs.class));
            //如果Exg地图CD存在
            if (exgResult.isPresent()) {
                ExgMapDTO map = exgResult.get();
                // 判断减去冷却后是否可预定 和截至时间
                // 定义日期时间格式
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                LocalDateTime lastRun = LocalDateTime.parse(map.getLastRun(), formatter);
                // 计算加上分钟数后的日期时间
                Duration duration = Duration.ofMinutes(map.getCooldownMinute());
                LocalDateTime newTime = lastRun.plus(duration);
                map.setDeadline(newTime.toString());
                // 获取当前时间（考虑时区，这里使用UTC作为示例）
                ZonedDateTime now = ZonedDateTime.now();
                LocalDateTime currentTime = now.toLocalDateTime();
                // 时间是否小于当前时间 判断是否可预定
                map.setIsOrder(newTime.isBefore(currentTime));
                // 处理找到的对象
                gameMapVo.setExgMap(map);
            }
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

    /**
     * 查询所有游戏地图名称。
     */
    @Override
    public List<String> listMapName() {
        return gameMapMapper.selectAll().stream().map(GameMap::getMapName).toList();
    }

    /**
     * 查询全部地图配置项。
     */
    @Override
    public List<Options<String>> allMapNames() {
        List<GameMap> gameMapList = gameMapMapper.selectAll();
        return gameMapList.stream()
                .map(item -> Options.<String>builder()
                        .label(item.getMapLabel()+"("+item.getMapName()+")")
                        .value(String.valueOf(item.getId()))
                        .build())
                .toList();
    }

    /**
     * 查询全部地图模型配置项。
     */
    @Override
    public List<Options<String>> allMapModes() {
        QueryWrapper queryWrapper = new QueryWrapper()
                .isNotNull("map_mode_url")
                .ne("map_mode_url","");
        List<GameMap> gameMapList = gameMapMapper.selectListByQuery(queryWrapper);
        return gameMapList.stream()
                .map(item -> Options.<String>builder()
                        .label(item.getMapLabel()+"("+item.getMapName()+")")
                        .value(item.getMapModeUrl())
                        .build())
                .toList();
    }
}
