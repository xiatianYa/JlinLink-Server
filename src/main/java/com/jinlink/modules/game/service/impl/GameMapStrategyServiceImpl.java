package com.jinlink.modules.game.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.domain.Options;
import com.jinlink.common.em.ExamineStatus;
import com.jinlink.common.exception.JinLinkException;
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

import java.io.Serializable;
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
        gameMapStrategy.setStatus(ExamineStatus.GameMapStrategyStatusEnum.Unaudited.getValue());
        gameMapStrategyMapper.insert(gameMapStrategy);
        return true;
    }

    /**
     * 发布文章。
     */
    @Override
    public Boolean pushMapStrategyById(GameMapStrategy gameMapStrategy) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        if (ObjectUtil.isNull(gameMapStrategy) & ObjectUtil.isNull(gameMapStrategy.getId())){
            throw new JinLinkException("非法参数");
        }
        GameMapStrategy one = gameMapStrategyMapper.selectOneById(gameMapStrategy.getId());
        if (ObjectUtil.isNull(loginIdAsLong)){
            throw new JinLinkException("用户不存在");
        }
        if (ObjectUtil.isNull(one)){
            throw new JinLinkException("攻略不存在!");
        }
        if (!one.getCreateUserId().equals(loginIdAsLong)){
            throw new JinLinkException("禁止操作他人文章!");
        }
        if (one.getStatus().equals(ExamineStatus.GameMapStrategyStatusEnum.PREVIEW.getValue())){
            throw new JinLinkException("攻略已在审核中,请勿重复提交!");
        }
        if (one.getStatus().equals(ExamineStatus.GameMapStrategyStatusEnum.APPROVED.getValue())){
            throw new JinLinkException("攻略已审核通过,请勿重复提交!");
        }
        one.setStatus(ExamineStatus.GameMapStrategyStatusEnum.PREVIEW.getValue());
        return true;
    }

    /**
     * 根据主键删除地图攻略表。
     */
    @Override
    public Boolean removeMapStrategyById(Serializable id) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        if (ObjectUtil.isNull(id)){
            throw new JinLinkException("非法参数");
        }
        GameMapStrategy one = gameMapStrategyMapper.selectOneById(id);
        if (ObjectUtil.isNull(loginIdAsLong)){
            throw new JinLinkException("用户不存在");
        }
        if (ObjectUtil.isNull(one)){
            throw new JinLinkException("攻略不存在!");
        }
        if (!one.getCreateUserId().equals(loginIdAsLong)){
            throw new JinLinkException("禁止操作他人文章!");
        }
        if (one.getStatus().equals(ExamineStatus.GameMapStrategyStatusEnum.APPROVED.getValue())){
            throw new JinLinkException("攻略审核通过,禁止修改!");
        }
        gameMapStrategyMapper.deleteById(id);
        return true;
    }

    /**
     * 根据主键更新地图攻略表。
     */
    @Override
    public Boolean updateMapStrategyById(GameMapStrategy gameMapStrategy) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        if (ObjectUtil.isNull(gameMapStrategy) & ObjectUtil.isNull(gameMapStrategy.getId())){
            throw new JinLinkException("非法参数");
        }
        GameMapStrategy one = gameMapStrategyMapper.selectOneById(gameMapStrategy.getId());
        if (ObjectUtil.isNull(loginIdAsLong)){
            throw new JinLinkException("用户不存在");
        }
        if (ObjectUtil.isNull(one)){
            throw new JinLinkException("攻略不存在!");
        }
        if (!one.getCreateUserId().equals(loginIdAsLong)){
            throw new JinLinkException("禁止操作他人文章!");
        }
        gameMapStrategyMapper.update(gameMapStrategy);
        return true;
    }

    /**
     * 根据地图攻略表主键获取详细信息。
     */
    @Override
    public GameMapStrategyVo getMapStrategyInfoById(Serializable id) {
        //查询所有地图
        List<Options<String>> options = gameMapService.allMapNames();
        GameMapStrategy gameMapStrategy = gameMapStrategyMapper.selectOneById(id);
        GameMapStrategyVo gameMapStrategyVo = BeanUtil.copyProperties(gameMapStrategy, GameMapStrategyVo.class);
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
        return gameMapStrategyVo;
    }

    /**
     * 游戏攻略审核
     */
    @Override
    public Boolean examineMapStrategyById(Long id, String type) {
        if (ObjectUtil.isNull(id) | ObjectUtil.isNull(type)){
            throw new JinLinkException("非法参数");
        }
        GameMapStrategy gameMapStrategy = gameMapStrategyMapper.selectOneById(id);
        if (ObjectUtil.isNull(gameMapStrategy)){
            throw new JinLinkException("游戏攻略不存在");
        }
        if (type.equals("pass")){
            gameMapStrategy.setStatus(ExamineStatus.GameMapStrategyStatusEnum.APPROVED.getValue());
        }else{
            gameMapStrategy.setStatus(ExamineStatus.GameMapStrategyStatusEnum.REVIEWABLE.getValue());
        }
        gameMapStrategyMapper.update(gameMapStrategy);
        return true;
    }
}
