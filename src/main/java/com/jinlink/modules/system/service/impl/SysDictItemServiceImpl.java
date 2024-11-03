package com.jinlink.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.dto.SysDictItemSearchDTO;
import com.jinlink.modules.system.entity.vo.SysDictItemVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysDictItem;
import com.jinlink.modules.system.mapper.SysDictItemMapper;
import com.jinlink.modules.system.service.SysDictItemService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 数据字典子项管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {
    @Resource
    private SysDictItemMapper sysDictItemMapper;

    @Override
    public Page<SysDictItemVO> listSysDictItemPage(PageQuery pageQuery, SysDictItemSearchDTO sysDictItemSearchDTO) {
        QueryWrapper sysDictItemQuery = new QueryWrapper()
                .eq("dict_id", sysDictItemSearchDTO.getDictId())
                .eq("value", sysDictItemSearchDTO.getValue())
                .like("zh_cn", sysDictItemSearchDTO.getZhCn())
                .like("en_us",sysDictItemSearchDTO.getEnUs())
                .like("description",sysDictItemSearchDTO.getDescription());
        Page<SysDictItem> paginate = sysDictItemMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), sysDictItemQuery);
        List<SysDictItem> paginateRecords = paginate.getRecords();
        List<SysDictItemVO> sysDictItemVOS = BeanUtil.copyToList(paginateRecords, SysDictItemVO.class);
        return new Page<>(sysDictItemVOS, paginate.getPageNumber(), paginate.getPageSize(),sysDictItemVOS.size());
    }
}
