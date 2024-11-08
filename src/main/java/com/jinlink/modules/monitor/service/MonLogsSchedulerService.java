package com.jinlink.modules.monitor.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.monitor.entity.dto.MonLogsSchedulerSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsSchedulerVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.monitor.entity.MonLogsScheduler;

/**
 * 调度日志 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface MonLogsSchedulerService extends IService<MonLogsScheduler> {

    Page<MonLogsSchedulerVo> listMonLogsSchedulerPage(PageQuery query, MonLogsSchedulerSearchDTO monLogsSchedulerSearchDTO);


    /**
     * 清空调度日志。
     */
    Boolean clearAll();
}
