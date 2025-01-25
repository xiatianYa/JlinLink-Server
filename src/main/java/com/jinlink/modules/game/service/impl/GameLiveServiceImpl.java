package com.jinlink.modules.game.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.util.BiliUtils;
import com.jinlink.common.util.file.ImageUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Value("${qiniu.domain}")
    private String domain;

    @Value("${qiniu.basePath}")
    private String basePath;

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

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

    /**
     * 添加主播入驻
     */
    @Override
    public Boolean saveLive(GameLive gameLive) {
        Long loginIdAsLong = StpUtil.getLoginIdAsLong();
        String uid = gameLive.getUid();
        if (ObjectUtil.isNotNull(gameLiveMapper.selectOneByQuery(new QueryWrapper().eq("create_user_id", loginIdAsLong)))){
            throw new JinLinkException("你已经入驻过了,不能再次入驻!");
        }
        if (ObjectUtil.isNotNull(gameLiveMapper.selectOneByQuery(new QueryWrapper().eq("uid", uid)))){
            throw new JinLinkException("当前主播已入驻,禁止重复添加!");
        }
        //获取主播B站信息
        String bgPath = null;
        try {
            bgPath = JSONObject.parseObject(BiliUtils.getBiliLiveApi(gameLive.getUid()))
                    .getJSONObject("data")
                    .getJSONObject("by_room_ids")
                    .getJSONObject(gameLive.getUid())
                    .getString("cover");
        }catch (Exception e){
            throw new JinLinkException("B站信息检索失败,请检查UID是否存在!");
        }
        String avatarPath = BiliUtils.getBiliLiveUserInfoApi(gameLive.getUid());
        //获取背景
        String bgFilePath = basePath + uid +"bg.jpg";
        String bgUrl = domain + ImageUtils.downloadImageAsResource(bgFilePath,accessKey,secretKey,bucket,bgPath);
        //获取头像
        String avatarFilePath = basePath + uid +".jpg";
        String avatarUrl = domain + ImageUtils.downloadImageAsResource(avatarFilePath,accessKey,secretKey,bucket,avatarPath);
        gameLive.setBgUrl(bgUrl);
        gameLive.setAvatar(avatarUrl);
        return save(gameLive);
    }
}
