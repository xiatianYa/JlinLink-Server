package com.jinlink.modules.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.monitor.entity.dto.MonLogsSchedulerSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsSchedulerVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.monitor.entity.MonLogsScheduler;
import com.jinlink.modules.monitor.mapper.MonLogsSchedulerMapper;
import com.jinlink.modules.monitor.service.MonLogsSchedulerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Page<MonLogsSchedulerVo> listMonLogsSchedulerPage(PageQuery query, MonLogsSchedulerSearchDTO monLogsSchedulerSearchDTO) {
        Page<MonLogsScheduler> paginate = monLogsSchedulerMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper()
                .eq("job_name", monLogsSchedulerSearchDTO.getJobName())
                .orderBy("create_time", false));
        List<MonLogsScheduler> records = paginate.getRecords();
        List<MonLogsSchedulerVo> monLogsSchedulerVos = BeanUtil.copyToList(records, MonLogsSchedulerVo.class);
        return new Page<>(monLogsSchedulerVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow());
    }

    /**
     * 清空调度日志。
     */
    @Override
    public Boolean clearAll() {
        List<Long> Ids = monLogsSchedulerMapper.selectAll().stream().map(MonLogsScheduler::getId).toList();
        return removeByIds(Ids);
    }
}
