package com.jinlink.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.SysDictItem;
import com.jinlink.modules.system.entity.dto.SysDictAddDTO;
import com.jinlink.modules.system.entity.dto.SysDictUpdateDTO;
import com.jinlink.modules.system.entity.vo.SysDictItemOptionsVo;
import com.jinlink.modules.system.entity.vo.SysDictVo;
import com.jinlink.modules.system.service.SysDictItemService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.jinlink.modules.system.entity.SysDict;
import com.jinlink.modules.system.mapper.SysDictMapper;
import com.jinlink.modules.system.service.SysDictService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据字典管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
@CacheConfig(cacheNames = "SysDict")
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    @Resource
    private SysDictMapper sysDictMapper;
    @Resource
    private SysDictItemService sysDictItemService;

    /**
     * 获取字典Map
     */
    @Override
    public Map<String, List<SysDictItemOptionsVo>> queryAllDictMap() {
        //查询所有字典
        List<SysDict> sysDicts = sysDictMapper.selectAll();
        //查询所有子字典
        List<SysDictItem> sysDictItems = sysDictItemService.list();
        //查询所有Map
        return getDictItemOptions(sysDicts,sysDictItems);
    }

    /**
     * 获取字典详细信息
     */
    @Override
    public SysDictVo getInfo(Serializable id) {
        SysDict sysDict = sysDictMapper.selectOneById(id);
        return BeanUtil.copyProperties(sysDict, SysDictVo.class);
    }

    /**
     * 删除字典多个
     */
    @Override
    public Boolean removeDictByIds(List<Long> ids) {
        //删除子字典
        sysDictItemService.remove(new QueryWrapper().in("dict_id", ids));
        return removeByIds(ids);
    }

    /**
     * 删除字典
     */
    @Override
    public Boolean removeDictById(Serializable id) {
        //删除子字典
        sysDictItemService.remove(new QueryWrapper().eq("dict_id", id));
        return removeById(id);
    }

    @Override
    public Boolean updateDictById(SysDict sysDict) {
        return updateById(sysDict);
    }

    /**
     * 分页查询数据字典管理
     */
    @Override
    public Page<SysDictVo> listDictPage(PageQuery query) {
        Page<SysDict> paginate = sysDictMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper());
        List<SysDict> records = paginate.getRecords();
        List<SysDictVo> sysDictVos = BeanUtil.copyToList(records, SysDictVo.class);
        return new Page<>(sysDictVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow());
    }

    @Override
    public Boolean saveDict(SysDictAddDTO sysDictAddDTO) {
        SysDict sysDict = BeanUtil.copyProperties(sysDictAddDTO, SysDict.class);
        if (ObjectUtil.isNull(sysDict)){
            throw new JinLinkException("参数异常!");
        }
        return save(sysDict);
    }

    /**
     * 根据主键更新数据字典管理。
     */
    @Override
    public Boolean updateDict(SysDictUpdateDTO sysDictUpdateDTO) {
        SysDict sysDict = BeanUtil.copyProperties(sysDictUpdateDTO, SysDict.class);
        if (ObjectUtil.isNull(sysDict)){
            throw new JinLinkException("参数异常!");
        }
        return updateById(sysDict);
    }

    /**
     * 构建字典MapVo
     */
    public Map<String, List<SysDictItemOptionsVo>> getDictItemOptions(List<SysDict> sysDicts, List<SysDictItem> sysDictItems) {
        return sysDicts.stream()
                .collect(Collectors.toMap(
                        SysDict::getCode, // 使用dict的code作为键
                        dict -> sysDictItems.stream() // 对每个dict，过滤sysDictItems
                                .filter(dictItem -> dictItem.getDictId().equals(dict.getId())) // 保留与dictId匹配的项
                                .map(dictItem -> { // 将每个匹配的dictItem映射到SysDictItemOptionsVO
                                    SysDictItemOptionsVo vo = new SysDictItemOptionsVo();
                                    vo.setLabel(dictItem.getZhCn());
                                    vo.setValue(dictItem.getValue());
                                    vo.setType(dictItem.getType());
                                    vo.setSort(dictItem.getSort());
                                    return vo;
                                })
                                .collect(Collectors.toList()) // 收集映射后的VO列表
                ));
    }
}
