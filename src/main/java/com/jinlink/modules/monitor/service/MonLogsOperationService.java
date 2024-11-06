package com.jinlink.modules.monitor.service;

import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsOperationSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsOperationVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.monitor.entity.MonLogsOperation;

/**
 * 操作日志 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface MonLogsOperationService extends IService<MonLogsOperation> {

    /**
     * 分页查询操作日志。
     */
    RPage<MonLogsOperationVo> listMonLogsOperationPage(PageQuery pageQuery, MonLogsOperationSearchDTO monLogsOperationSearchDTO);

    /**
     * 清空操作日志。
     */
    Boolean clearAll();
}
