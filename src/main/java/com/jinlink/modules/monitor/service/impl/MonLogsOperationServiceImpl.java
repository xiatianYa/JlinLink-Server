package com.jinlink.modules.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.domain.Options;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsOperationSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsOperationVo;
import com.jinlink.modules.system.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.monitor.entity.MonLogsOperation;
import com.jinlink.modules.monitor.mapper.MonLogsOperationMapper;
import com.jinlink.modules.monitor.service.MonLogsOperationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 操作日志 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class MonLogsOperationServiceImpl extends ServiceImpl<MonLogsOperationMapper, MonLogsOperation> implements MonLogsOperationService {
    @Resource
    private MonLogsOperationMapper monLogsOperationMapper;
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询操作日志。
     */
    @Override
    public RPage<MonLogsOperationVo> listMonLogsOperationPage(PageQuery pageQuery, MonLogsOperationSearchDTO monLogsOperationSearchDTO) {
        //查询全部用户名称
        List<Options<String>> allUserNames = sysUserService.getAllUserNames();
        Page<MonLogsOperation> paginate = monLogsOperationMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(),new QueryWrapper()
                .eq("create_user_id",monLogsOperationSearchDTO.getCreateUser())
                .orderBy("create_time",false));
        List<MonLogsOperation> records = paginate.getRecords();
        List<MonLogsOperationVo> monLogsOperationVos = BeanUtil.copyToList(records, MonLogsOperationVo.class);
        monLogsOperationVos.forEach(item->{
            // 使用Stream API查找第一个value为1的元素  
            Optional<Options<String>> sysUserName = allUserNames.stream()
                    .filter(sysUser -> sysUser.getValue().equals(String.valueOf(item.getCreateUserId())))
                    .findFirst();
            //如果找到了
            sysUserName.ifPresent(stringOptions -> item.setCreateUser(stringOptions.getLabel()));
        });
        return RPage.build(new Page<>(monLogsOperationVos, paginate.getPageNumber(), paginate.getPageSize(),paginate.getTotalRow()));
    }

    @Override
    public Boolean clearAll() {
        List<Long> ids = monLogsOperationMapper.selectAll().stream().map(MonLogsOperation::getId).toList();
        return removeByIds(ids);
    }
}
