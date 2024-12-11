package com.jinlink.modules.monitor.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.monitor.entity.dto.MonLogsLoginSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsLoginVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.monitor.entity.MonLogsLogin;

/**
 * 登录日志 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface MonLogsLoginService extends IService<MonLogsLogin> {

    /**
     * 分页查询登陆日志。
     */
    Page<MonLogsLoginVo> listMonLogsLoginPage(PageQuery query, MonLogsLoginSearchDTO monLogsLoginSearchDTO);

    /**
     * 清空登录日志。
     */
    Boolean clearAll();
}
