package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.entity.GameGame;
import com.jinlink.modules.game.entity.GameMode;
import com.jinlink.modules.game.entity.dto.GameServerSearchDTO;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.jinlink.modules.game.service.GameCommunityService;
import com.jinlink.modules.game.service.GameGameService;
import com.jinlink.modules.game.service.GameModeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.mapper.GameServerMapper;
import com.jinlink.modules.game.service.GameServerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 游戏服务器表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameServerServiceImpl extends ServiceImpl<GameServerMapper, GameServer> implements GameServerService {
    @Resource
    private GameServerMapper gameServerMapper;
    @Resource
    private GameCommunityService gameCommunityService;
    @Resource
    private GameModeService gameModeService;
    @Resource
    private GameGameService gameGameService;

    /**
     * 分页查询游戏服务器表。
     */
    @Override
    public RPage<GameServerVo> listGameServerVoPage(PageQuery pageQuery, GameServerSearchDTO gameServerSearchDTO) {
        Page<GameServer> paginate = gameServerMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper()
                .eq("community_id", gameServerSearchDTO.getCommunityId())
                .eq("mode_id", gameServerSearchDTO.getModeId())
                .eq("game_id", gameServerSearchDTO.getGameId())
                .orderBy("community_id", false));
        List<GameServer> records = paginate.getRecords();
        List<GameServerVo> gameServerVos = BeanUtil.copyToList(records, GameServerVo.class);
        //查询所有社区
        List<GameCommunity> gameCommunityList = gameCommunityService.list();
        //查询所有游戏模式
        List<GameMode> gameModeList = gameModeService.list();
        //查询所有游戏名称
        List<GameGame> gameGameList = gameGameService.list();
        gameServerVos.forEach(item->{
            //查询当前对象下社区名称 | 游戏模式 | 游戏名称
            Optional<GameCommunity> gameCommunity = gameCommunityList.stream().filter(community -> item.getCommunityId().equals(String.valueOf(community.getId()))).findFirst();
            Optional<GameMode> gameMode = gameModeList.stream().filter(mode -> item.getModeId().equals(String.valueOf(mode.getId()))).findFirst();
            Optional<GameGame> gameGame = gameGameList.stream().filter(game -> item.getGameId().equals(String.valueOf(game.getId()))).findFirst();
            gameCommunity.ifPresent(community -> item.setCommunityName(community.getCommunityName()));
            gameMode.ifPresent(mode -> item.setModeName(mode.getModeName()));
            gameGame.ifPresent(game -> item.setGameName(game.getGameName()));
        });
        return RPage.build(new Page<>(gameServerVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }
}
