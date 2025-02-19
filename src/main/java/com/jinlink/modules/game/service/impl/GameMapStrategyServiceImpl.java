package com.jinlink.modules.game.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.domain.Options;
import com.jinlink.common.em.ExamineStatus;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameMapStrategyAddDTO;
import com.jinlink.modules.game.entity.vo.GameMapStrategyVo;
import com.jinlink.modules.game.service.GameMapService;
import com.jinlink.modules.system.entity.SysUser;
import com.jinlink.modules.system.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameMapStrategy;
import com.jinlink.modules.game.mapper.GameMapStrategyMapper;
import com.jinlink.modules.game.service.GameMapStrategyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    @Resource
    private GameMapService gameMapService;
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询地图攻略表。
     */
    @Override
    public RPage<GameMapStrategyVo> listGameMapStrategyVoPage(PageQuery pageQuery) {
        //查询所有地图
        List<Options<String>> options = gameMapService.allMapNames();
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<GameMapStrategy> paginate = gameMapStrategyMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(),queryWrapper);
        List<GameMapStrategy> records = paginate.getRecords();
        List<GameMapStrategyVo> gameMapStrategyVos = BeanUtil.copyToList(records, GameMapStrategyVo.class);
        gameMapStrategyVos.forEach(gameMapStrategyVo -> {
            //查询用户名称
            SysUser sysUser = sysUserService.getById(gameMapStrategyVo.getCreateUserId());
            if (ObjectUtil.isNotNull(sysUser)){
                gameMapStrategyVo.setCreateUserName(sysUser.getNickName());
            }
            //查询地图名称
            Optional<Options<String>> first = options.stream().filter(map -> map.getValue().equals(String.valueOf(gameMapStrategyVo.getMapId()))).findFirst();
            if (first.isPresent()) {
                String mapLabel = first.get().getLabel();
                gameMapStrategyVo.setMapLabel(mapLabel);
            }
        });
        return RPage.build(new Page<>(gameMapStrategyVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }

    /**
     * 添加地图攻略表。
     */
    @Override
    public Boolean saveMapStrategy(GameMapStrategyAddDTO gameMapStrategyAddDTO) {
        GameMapStrategy gameMapStrategy = BeanUtil.copyProperties(gameMapStrategyAddDTO, GameMapStrategy.class);
        //设置攻略状态
        gameMapStrategy.setStatus(ExamineStatus.GameMapStrategyStatusEnum.PREVIEW.getValue());
        gameMapStrategyMapper.insert(gameMapStrategy);
        return true;
    }
}
