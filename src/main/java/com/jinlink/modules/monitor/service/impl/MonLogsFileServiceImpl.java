package com.jinlink.modules.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.file.entity.dto.MonLogsFileSearchDTO;
import com.jinlink.modules.monitor.entity.MonLogsLogin;
import com.jinlink.modules.monitor.entity.vo.MonLogsFileVo;
import com.jinlink.modules.monitor.entity.vo.MonLogsLoginVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.monitor.entity.MonLogsFile;
import com.jinlink.modules.monitor.mapper.MonLogsFileMapper;
import com.jinlink.modules.monitor.service.MonLogsFileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件上传日志 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class MonLogsFileServiceImpl extends ServiceImpl<MonLogsFileMapper, MonLogsFile> implements MonLogsFileService {
    @Resource
    private MonLogsFileMapper monLogsFileMapper;

    /**
     * 分页查询文件上传日志。
     */
    @Override
    public RPage<MonLogsFileVo> listMonLogsFileVoPage(PageQuery pageQuery, MonLogsFileSearchDTO monLogsFileSearchDTO) {
        Page<MonLogsFile> paginate = monLogsFileMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper()
                .in("user_id",monLogsFileSearchDTO.getUserName()));
        List<MonLogsFile> records = paginate.getRecords();
        List<MonLogsFileVo> monLogsFileVos = BeanUtil.copyToList(records, MonLogsFileVo.class);
        return RPage.build(new Page<>(monLogsFileVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }

    /**
     * 清空文件日志。
     */
    @Override
    public Boolean clearAll() {
        List<Long> Ids = monLogsFileMapper.selectAll().stream().map(MonLogsFile::getId).toList();
        return removeByIds(Ids);
    }
}
