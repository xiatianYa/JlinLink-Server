package com.jinlink.controller.game;

import com.jinlink.common.api.Result;
import com.jinlink.modules.game.entity.vo.GameChatRecordVo;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.jinlink.modules.game.entity.GameChat;
import com.jinlink.modules.game.service.GameChatService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 聊天室消息记录表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@RequestMapping("/gameChat")
public class GameChatController {

    @Resource
    private GameChatService gameChatService;

    /**
     * 查询聊天记录record。
     */
    @GetMapping("record")
    public Result<List<GameChatRecordVo>> record(Integer index, Integer num) {
        return Result.success("请求成功",gameChatService.record(index,num));
    }
}
