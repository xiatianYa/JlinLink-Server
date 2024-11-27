package com.jinlink.controller.bot;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.bot.entity.dto.BotGroupAddDTO;
import com.jinlink.modules.bot.entity.vo.BotGroupVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jinlink.modules.bot.entity.BotGroup;
import com.jinlink.modules.bot.service.BotGroupService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 入驻群管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "群管理")
@RequiredArgsConstructor
@RequestMapping("/botGroup")
public class BotGroupController {

    @NonNull
    private BotGroupService botGroupService;

    /**
     * 添加入驻群管理。
     *
     * @param botGroup 入驻群管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增群")
    @SaCheckPermission("bot:botGroup:save")
    public Result<Boolean> save(@Parameter(description = "群对象", required = true)@RequestBody BotGroupAddDTO botGroup) {
        return Result.success("请求成功",botGroupService.save(BeanUtil.copyProperties(botGroup,BotGroup.class)));
    }

    /**
     * 根据主键删除入驻群管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除群")
    @SaCheckPermission("bot:botGroup:delete")
    public Result<Boolean> remove(@Parameter(description = "群ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",botGroupService.removeById(id));
    }

    /**
     * 根据主键更新入驻群管理。
     *
     * @param botGroup 入驻群管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改群")
    @SaCheckPermission("bot:botGroup:update")
    public Result<Boolean> update(@Parameter(description = "群对象", required = true)@RequestBody BotGroup botGroup) {
        return Result.success("请求成功",botGroupService.updateById(botGroup));
    }

    /**
     * 查询所有入驻群管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有群")
    @SaCheckPermission("bot:botGroup:list")
    public Result<List<BotGroupVo>> list() {
        return Result.data(BeanUtil.copyToList(botGroupService.list(), BotGroupVo.class));
    }

    /**
     * 根据入驻群管理主键获取详细信息。
     *
     * @param id 入驻群管理主键
     * @return 入驻群管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取群详细")
    @SaCheckPermission("bot:botGroup:info")
    public Result<BotGroupVo> getInfo(@Parameter(description = "群ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(botGroupService.getById(id), BotGroupVo.class));
    }

    /**
     * 分页查询入驻群管理。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询群")
    @SaCheckPermission("bot:botGroup:page")
    public Result<RPage<BotGroupVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery) {
        RPage<BotGroupVo> botGroupVoRPage = botGroupService.listBotGroupVoPage(pageQuery);
        return Result.data(botGroupVoRPage);
    }

}
