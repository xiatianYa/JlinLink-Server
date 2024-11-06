package com.jinlink.modules.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.monitor.entity.dto.MonLogsLoginSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsLoginVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.monitor.entity.MonLogsLogin;
import com.jinlink.modules.monitor.mapper.MonLogsLoginMapper;
import com.jinlink.modules.monitor.service.MonLogsLoginService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Page<MonLogsLoginVo> listMonLogsLoginPage(PageQuery query, MonLogsLoginSearchDTO monLogsLoginSearchDTO) {
        Page<MonLogsLogin> paginate = monLogsLoginMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper()
                .like("user_name", monLogsLoginSearchDTO.getUserName())
                .orderBy("create_time", false));
        List<MonLogsLogin> records = paginate.getRecords();
        List<MonLogsLoginVo> monLogsLoginVos = BeanUtil.copyToList(records, MonLogsLoginVo.class);
        return new Page<>(monLogsLoginVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow());
    }

    /**
     * 清空登录日志。
     */
    @Override
    public Boolean clearAll() {
        List<Long> Ids = monLogsLoginMapper.selectAll().stream().map(MonLogsLogin::getId).toList();
        return removeByIds(Ids);
    }
}
