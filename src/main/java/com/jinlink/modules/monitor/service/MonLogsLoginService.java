package com.jinlink.modules.monitor.service;

import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.monitor.entity.dto.MonLogsLoginSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsLoginVo;
import com.jinlink.modules.monitor.mapper.MonLogsLoginMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.monitor.entity.MonLogsLogin;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 登录日志 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface MonLogsLoginService extends IService<MonLogsLogin> {

    Page<MonLogsLoginVo> listMonLogsLoginPage(PageQuery query, MonLogsLoginSearchDTO monLogsLoginSearchDTO);
}
