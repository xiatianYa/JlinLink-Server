package com.jinlink.modules.monitor.service.impl;

import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.monitor.entity.dto.MonLogsSchedulerSearchDTO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.monitor.entity.MonLogsScheduler;
import com.jinlink.modules.monitor.mapper.MonLogsSchedulerMapper;
import com.jinlink.modules.monitor.service.MonLogsSchedulerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 调度日志 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class MonLogsSchedulerServiceImpl extends ServiceImpl<MonLogsSchedulerMapper, MonLogsScheduler> implements MonLogsSchedulerService {
    @Resource
    private MonLogsSchedulerMapper monLogsSchedulerMapper;

    @Override
    public Page<MonLogsScheduler> listMonLogsSchedulerPage(PageQuery query, MonLogsSchedulerSearchDTO monLogsSchedulerSearchDTO) {
        return monLogsSchedulerMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper().eq("job_name", monLogsSchedulerSearchDTO.getJobName()));
    }
}
