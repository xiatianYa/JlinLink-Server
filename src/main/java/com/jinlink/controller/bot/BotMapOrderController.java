package com.jinlink.controller.bot;

import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.jinlink.modules.bot.entity.BotMapOrder;
import com.jinlink.modules.bot.service.BotMapOrderService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 地图订阅表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "地图订阅")
@RequiredArgsConstructor
@RequestMapping("/botMapOrder")
public class BotMapOrderController {

    @NonNull
    private BotMapOrderService botMapOrderService;

    /**
     * 添加地图订阅表。
     *
     * @param botMapOrder 地图订阅表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody BotMapOrder botMapOrder) {
        return botMapOrderService.save(botMapOrder);
    }

    /**
     * 根据主键删除地图订阅表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return botMapOrderService.removeById(id);
    }

    /**
     * 根据主键更新地图订阅表。
     *
     * @param botMapOrder 地图订阅表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody BotMapOrder botMapOrder) {
        return botMapOrderService.updateById(botMapOrder);
    }

    /**
     * 查询所有地图订阅表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<BotMapOrder> list() {
        return botMapOrderService.list();
    }

    /**
     * 根据地图订阅表主键获取详细信息。
     *
     * @param id 地图订阅表主键
     * @return 地图订阅表详情
     */
    @GetMapping("getInfo/{id}")
    public BotMapOrder getInfo(@PathVariable Serializable id) {
        return botMapOrderService.getById(id);
    }

    /**
     * 分页查询地图订阅表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<BotMapOrder> page(Page<BotMapOrder> page) {
        return botMapOrderService.page(page);
    }

}
