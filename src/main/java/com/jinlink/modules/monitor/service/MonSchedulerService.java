package com.jinlink.modules.monitor.service;

import com.jinlink.common.domain.Options;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonSchedulerSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonSchedulerVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.monitor.entity.MonScheduler;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 调度管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface MonSchedulerService extends IService<MonScheduler> {

    /**
     * 开启/关闭定时器任务
     */
    void startOrStopScheduler(MonScheduler monScheduler);

    /**
     * 修改定时器任务
     */
    @Transactional
    Boolean updateSchedulerById(MonScheduler monScheduler);

    /**
     * 新增定时任务
     */
    @Transactional
    Boolean saveScheduler(MonScheduler monScheduler);

    /**
     * 删除单个定时任务
     */
    @Transactional
    Boolean removeSchedulerById(Serializable id);

    /**
     * 删除多个定时任务
     */
    @Transactional
    Boolean removeSchedulerByIds(List<Long> ids);

    /**
     * 分页查询定时任务
     */
    RPage<MonSchedulerVo> listMonSchedulerPage(PageQuery query, MonSchedulerSearchDTO monSchedulerSearchDTO);

    /**
     * 查询全部调度任务名称
     */
    List<Options<String>> allJobNames();
}
