package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
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

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.path}")
    private String localFilePath;

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
     * 查询所有入驻主播。
     */
    @Override
    public List<GameLiveVo> listAll() {
        //返回列表
        List<GameLiveVo> biliUserDataVos = new ArrayList<>();
        return List.of();
    }

    /**
     * 添加主播入驻
     */
    @Override
    public Boolean saveLive(GameLive gameLive) {
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
        String bgUrl = ImageUtils.downloadImageAsResource(bgPath,localFilePath+"/live/", gameLive.getUid()+"bg.jpg");
        //获取头像
        String avatarUrl = ImageUtils.downloadImageAsResource(avatarPath,localFilePath+"/live/", gameLive.getUid()+".jpg");
        gameLive.setBgUrl(bgUrl);
        gameLive.setAvatar(avatarUrl);
        return save(gameLive);
    }
}
