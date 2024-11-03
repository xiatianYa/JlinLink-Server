package com.jinlink.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.SysDictItem;
import com.jinlink.modules.system.entity.vo.SysDictItemOptionsVO;
import com.jinlink.modules.system.entity.vo.SysDictVO;
import com.jinlink.modules.system.service.SysDictItemService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysDict;
import com.jinlink.modules.system.mapper.SysDictMapper;
import com.jinlink.modules.system.service.SysDictService;
import jakarta.annotation.Resource;
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
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    @Resource
    private SysDictMapper sysDictMapper;
    @Resource
    private SysDictItemService sysDictItemService;

    /**
     * 获取字典Map
     */
    @Override
    public Map<String, List<SysDictItemOptionsVO>> queryAllDictMap() {
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
    public SysDictVO getInfo(Serializable id) {
        SysDict sysDict = sysDictMapper.selectOneById(id);
        return BeanUtil.copyProperties(sysDict,SysDictVO.class);
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
    public boolean removeDictById(Serializable id) {
        //删除子字典
        sysDictItemService.remove(new QueryWrapper().eq("dict_id", id));
        return removeById(id);
    }

    /**
     * 构建字典MapVo
     */
    public Map<String, List<SysDictItemOptionsVO>> getDictItemOptions(List<SysDict> sysDicts,List<SysDictItem> sysDictItems) {
        return sysDicts.stream()
                .collect(Collectors.toMap(
                        SysDict::getCode, // 使用dict的code作为键
                        dict -> sysDictItems.stream() // 对每个dict，过滤sysDictItems
                                .filter(dictItem -> dictItem.getDictId().equals(dict.getId())) // 保留与dictId匹配的项
                                .map(dictItem -> { // 将每个匹配的dictItem映射到SysDictItemOptionsVO
                                    SysDictItemOptionsVO vo = new SysDictItemOptionsVO();
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
