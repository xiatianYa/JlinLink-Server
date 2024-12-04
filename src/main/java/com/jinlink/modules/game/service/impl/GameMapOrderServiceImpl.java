package com.jinlink.modules.game.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.GameMap;
import com.jinlink.modules.game.entity.dto.GameMapOrderAddDTO;
import com.jinlink.modules.game.entity.dto.OrderTimeDTO;
import com.jinlink.modules.game.entity.vo.GameMapOrderVo;
import com.jinlink.modules.game.entity.vo.GameMapVo;
import com.jinlink.modules.game.service.GameMapService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameMapOrder;
import com.jinlink.modules.game.mapper.GameMapOrderMapper;
import com.jinlink.modules.game.service.GameMapOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 地图订阅表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameMapOrderServiceImpl extends ServiceImpl<GameMapOrderMapper, GameMapOrder> implements GameMapOrderService {

    @Resource
    private GameMapOrderMapper gameMapOrderMapper;
    @Resource
    private GameMapService gameMapService;

    /**
     * 分页查询地图订阅表。
     */
    @Override
    public RPage<GameMapOrderVo> listGameMapOrderVoPage(PageQuery pageQuery) {
        Page<GameMapOrder> paginate = gameMapOrderMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper());
        List<GameMapOrder> records = paginate.getRecords();
        //返回数据列表
        List<GameMapOrderVo> vos = new ArrayList<>();
        //查询全部地图
        List<GameMap> gameMapList = gameMapService.list();
        for (GameMapOrder record : records) {
            //查询出订阅的地图
            Optional<GameMap> gameMapOptional = gameMapList.stream()
                    .filter(map -> map.getId().equals(record.getMapId())).findFirst();
            if (gameMapOptional.isPresent()) {
                GameMapOrderVo build = GameMapOrderVo.builder()
                        .userId(record.getUserId())
                        .gameMapVo(BeanUtil.copyProperties(gameMapOptional.get(), GameMapVo.class))
                        .build();
                vos.add(build);
            }
        }
        return RPage.build(new Page<>(vos,pageQuery.getCurrent(),pageQuery.getSize(),paginate.getTotalRow()));
    }

    /**
     * 查询所有地图订阅表。
     */
    @Override
    public List<GameMapOrderVo> listGameMapOrderVo() {
        List<GameMapOrder> gameMapOrders = gameMapOrderMapper.selectAll();
        //查询全部地图
        List<GameMap> gameMapList = gameMapService.list();
        //返回数据列表
        List<GameMapOrderVo> vos = new ArrayList<>();
        for (GameMapOrder gameMapOrder : gameMapOrders) {
            //查询出订阅的地图
            Optional<GameMap> gameMapOptional = gameMapList.stream()
                    .filter(map -> map.getId().equals(gameMapOrder.getMapId())).findFirst();
            if (gameMapOptional.isPresent()) {
                GameMapOrderVo build = GameMapOrderVo.builder()
                        .userId(gameMapOrder.getUserId())
                        .gameMapVo(BeanUtil.copyProperties(gameMapOptional.get(), GameMapVo.class))
                        .build();
                vos.add(build);
            }
        }
        return vos;
    }

    /**
     * 查询用户所有地图订阅表。
     */
    @Override
    public List<GameMapOrderVo> listGameMapOrderVoByUser() {
        List<GameMapOrder> gameMapOrders = gameMapOrderMapper.selectListByQuery(new QueryWrapper()
                .eq("user_id", StpUtil.getLoginIdAsLong()));
        //查询全部地图
        List<GameMap> gameMapList = gameMapService.list();
        //返回数据列表
        List<GameMapOrderVo> vos = new ArrayList<>();
        for (GameMapOrder gameMapOrder : gameMapOrders) {
            //查询出订阅的地图
            Optional<GameMap> gameMapOptional = gameMapList.stream()
                    .filter(map -> map.getId().equals(gameMapOrder.getMapId())).findFirst();
            if (gameMapOptional.isPresent()) {
                GameMapOrderVo build = GameMapOrderVo.builder()
                        .id(gameMapOrder.getId())
                        .userId(gameMapOrder.getUserId())
                        .gameMapVo(BeanUtil.copyProperties(gameMapOptional.get(), GameMapVo.class,"artifact","tag"))
                        .orderTimes(JSON.parseArray(gameMapOrder.getOrderTimes(), OrderTimeDTO.class))
                        .build();
                vos.add(build);
            }
        }
        return vos;
    }

    /**
     * 添加地图订阅表。
     */
    @Override
    public Boolean saveGameMapOrder(GameMapOrderAddDTO gameMapOrderAddDTO) {
        //设置用户ID
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        if (ObjectUtil.isNotNull(loginIdAsLong)){
            gameMapOrderAddDTO.setUserId(loginIdAsLong);
        }else{
            throw new JinLinkException("用户ID获取失败");
        }
        GameMapOrder one = gameMapOrderMapper.selectOneByQuery(new QueryWrapper()
                .eq("user_id", loginIdAsLong)
                .eq("map_id", gameMapOrderAddDTO.getMapId())
                .eq("is_deleted",0));
        if (ObjectUtil.isNotNull(one)){
            throw new JinLinkException("你已添加过此地图!");
        }
        GameMapOrder gameMapOrder = BeanUtil.copyProperties(gameMapOrderAddDTO, GameMapOrder.class);
        gameMapOrder.setUserId(loginIdAsLong);
        gameMapOrderMapper.insert(gameMapOrder);
        return true;
    }
}
