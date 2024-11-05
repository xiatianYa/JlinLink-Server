package com.jinlink.modules.monitor.service;

import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsOperationSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsErrorVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.monitor.entity.MonLogsError;

/**
 * 错误异常日志 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface MonLogsErrorService extends IService<MonLogsError> {
    /**
     * 分页查询错误异常日志。
     */
    RPage<MonLogsErrorVo> listMonLogsErrorPage(PageQuery pageQuery, MonLogsOperationSearchDTO monLogsOperationSearchDTO);
}
