package com.jinlink.modules.monitor.service.impl;

import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.monitor.entity.dto.MonLogsLoginSearchDTO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.monitor.entity.MonLogsLogin;
import com.jinlink.modules.monitor.mapper.MonLogsLoginMapper;
import com.jinlink.modules.monitor.service.MonLogsLoginService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 登录日志 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class MonLogsLoginServiceImpl extends ServiceImpl<MonLogsLoginMapper, MonLogsLogin> implements MonLogsLoginService {
    @Resource
    private MonLogsLoginMapper monLogsLoginMapper;

    @Override
    public Page<MonLogsLogin> listMonLogsLoginPage(PageQuery query, MonLogsLoginSearchDTO monLogsLoginSearchDTO) {
        return monLogsLoginMapper.paginate(query.getCurrent(),query.getSize(),new QueryWrapper()
                .like("user_name",monLogsLoginSearchDTO.getUserName()).orderBy("create_time",false));
    }
}
