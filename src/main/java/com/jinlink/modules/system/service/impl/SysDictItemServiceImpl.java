package com.jinlink.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysDictItemAddDTO;
import com.jinlink.modules.system.entity.dto.SysDictItemSearchDTO;
import com.jinlink.modules.system.entity.dto.SysDictItemUpdateDTO;
import com.jinlink.modules.system.entity.dto.SysDictUpdateDTO;
import com.jinlink.modules.system.entity.vo.SysDictItemVo;
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
    public Page<SysDictItemVo> listSysDictItemPage(PageQuery pageQuery, SysDictItemSearchDTO sysDictItemSearchDTO) {
        QueryWrapper sysDictItemQuery = new QueryWrapper()
                .eq("dict_id", sysDictItemSearchDTO.getDictId())
                .eq("value", sysDictItemSearchDTO.getValue())
                .like("zh_cn", sysDictItemSearchDTO.getZhCn())
                .like("en_us",sysDictItemSearchDTO.getEnUs())
                .like("description",sysDictItemSearchDTO.getDescription());
        Page<SysDictItem> paginate = sysDictItemMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), sysDictItemQuery);
        List<SysDictItem> paginateRecords = paginate.getRecords();
        List<SysDictItemVo> sysDictItemVos = BeanUtil.copyToList(paginateRecords, SysDictItemVo.class);
        return new Page<>(sysDictItemVos, paginate.getPageNumber(), paginate.getPageSize(), sysDictItemVos.size());
    }


    /**
     * 添加数据字典子项管理。
     */
    @Override
    public Boolean saveDictItem(SysDictItemAddDTO sysDictItemAddDTO) {
        SysDictItem sysDictItem = BeanUtil.copyProperties(sysDictItemAddDTO, SysDictItem.class);
        if (ObjectUtil.isNull(sysDictItem)){
            throw new JinLinkException("参数异常!");
        }
        return save(sysDictItem);
    }

    /**
     * 根据主键更新数据字典子项管理。
     */
    @Override
    public Boolean updateDictItem(SysDictItemUpdateDTO sysDictItemUpdateDTO) {
        SysDictItem sysDictItem = BeanUtil.copyProperties(sysDictItemUpdateDTO, SysDictItem.class);
        if (ObjectUtil.isNull(sysDictItem)){
            throw new JinLinkException("参数异常!");
        }
        return updateById(sysDictItem);
    }
}
