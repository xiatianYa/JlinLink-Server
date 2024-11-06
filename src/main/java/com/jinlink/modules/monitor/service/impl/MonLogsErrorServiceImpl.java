package com.jinlink.modules.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.domain.Options;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsOperationSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsErrorVo;
import com.jinlink.modules.system.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.monitor.entity.MonLogsError;
import com.jinlink.modules.monitor.mapper.MonLogsErrorMapper;
import com.jinlink.modules.monitor.service.MonLogsErrorService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 错误异常日志 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class MonLogsErrorServiceImpl extends ServiceImpl<MonLogsErrorMapper, MonLogsError> implements MonLogsErrorService {
    @Resource
    private MonLogsErrorMapper monLogsErrorMapper;
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询错误异常日志。
     */
    @Override
    public RPage<MonLogsErrorVo> listMonLogsErrorPage(PageQuery pageQuery, MonLogsOperationSearchDTO monLogsOperationSearchDTO) {
        //查询全部用户名称
        List<Options<String>> allUserNames = sysUserService.getAllUserNames();
        Page<MonLogsError> paginate = monLogsErrorMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(),new QueryWrapper()
                .eq("create_user_id",monLogsOperationSearchDTO.getCreateUser())
                .orderBy("create_time",false));
        List<MonLogsError> records = paginate.getRecords();
        List<MonLogsErrorVo> monLogsErrorVOS = BeanUtil.copyToList(records, MonLogsErrorVo.class);
        monLogsErrorVOS.forEach(item->{
            // 使用Stream API查找第一个value为1的元素
            Optional<Options<String>> sysUserName = allUserNames.stream()
                    .filter(sysUser -> sysUser.getValue().equals(String.valueOf(item.getCreateUserId())))
                    .findFirst();
            //如果找到了
            sysUserName.ifPresent(stringOptions -> item.setCreateUser(stringOptions.getLabel()));
        });
        return RPage.build(new Page<>(monLogsErrorVOS, paginate.getPageNumber(), paginate.getPageSize(),paginate.getTotalRow()));
    }


    /**
     * 清空异常日志。
     */
    @Override
    public Boolean clearAll() {
        List<Long> Ids = monLogsErrorMapper.selectAll().stream().map(MonLogsError::getId).toList();
        return removeByIds(Ids);
    }
}
