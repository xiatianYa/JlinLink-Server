package com.jinlink.modules.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.domain.Options;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonSchedulerSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonSchedulerVo;
import com.jinlink.modules.monitor.service.QuartzService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.monitor.entity.MonScheduler;
import com.jinlink.modules.monitor.mapper.MonSchedulerMapper;
import com.jinlink.modules.monitor.service.MonSchedulerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 调度管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class MonSchedulerServiceImpl extends ServiceImpl<MonSchedulerMapper, MonScheduler> implements MonSchedulerService {

    @Resource
    private MonSchedulerMapper monSchedulerMapper;
    @Resource
    private QuartzService quartzService;

    /**
     * 开启/关闭定时器任务
     */
    @Override
    public void startOrStopScheduler(MonScheduler monScheduler) {
        if (ObjectUtil.isNull(monScheduler) | ObjectUtil.isNull(monScheduler.getJobName())
                | ObjectUtil.isNull(monScheduler.getJobGroup()) | ObjectUtil.isNull(monScheduler.getTriggerName())
                | ObjectUtil.isNull(monScheduler.getTriggerGroup()) | ObjectUtil.isNull(monScheduler.getStatus())
                | ObjectUtil.isNull(monScheduler.getCron()) | ObjectUtil.isNull(monScheduler.getJobClassName())){
            throw new JinLinkException("非法参数!");
        }

        if (monScheduler.getStatus().equals(1)){
            quartzService.addCronJob(monScheduler);
        }else{
            quartzService.deleteCronJob(monScheduler);
        }
    }

    /**
     * 修改定时器任务
     */
    @Override
    public Boolean updateSchedulerById(MonScheduler monScheduler) {

        //调用是否要开启定时任务
        if(updateById(monScheduler)) startOrStopScheduler(monScheduler);
        return true;
    }

    /**
     * 新增定时任务
     */
    @Override
    public Boolean saveScheduler(MonScheduler monScheduler) {
        //调用是否要开启定时任务
        startOrStopScheduler(monScheduler);
        return save(monScheduler);
    }

    /**
     * 删除单个定时任务
     */
    @Override
    public Boolean removeSchedulerById(Serializable id) {
        quartzService.deleteCronJob(getById(id));
        return removeById(id);
    }

    /**
     * 删除多个定时任务
     */
    @Override
    public Boolean removeSchedulerByIds(List<Long> ids) {
        ids.forEach(id->{
            quartzService.deleteCronJob(getById(id));
        });
        return removeByIds(ids);
    }

    /**
     * 分页查询定时任务
     */
    @Override
    public RPage<MonSchedulerVo> listMonSchedulerPage(PageQuery query, MonSchedulerSearchDTO monSchedulerSearchDTO) {
        Page<MonScheduler> paginate = monSchedulerMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper().like("job_name", monSchedulerSearchDTO.getJobName())
                .like("job_group", monSchedulerSearchDTO.getJobGroup())
                .like("trigger_name", monSchedulerSearchDTO.getTriggerName())
                .like("trigger_group", monSchedulerSearchDTO.getTriggerGroup()));
        List<MonScheduler> records = paginate.getRecords();
        List<MonSchedulerVo> monSchedulerVos = BeanUtil.copyToList(records, MonSchedulerVo.class);
        return RPage.build(new Page<>(monSchedulerVos, paginate.getPageNumber(), paginate.getPageSize(),monSchedulerVos.size()));
    }

    /**
     * 查询全部调度任务名称
     */
    @Override
    public List<Options<String>> allJobNames() {
        List<MonScheduler> monSchedulers = monSchedulerMapper.selectAll();
        return monSchedulers.stream()
                .map(item -> Options.<String>builder()
                        .label(item.getJobName())
                        .value(item.getJobName())
                        .build())
                .toList();
    }
}
