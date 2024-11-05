package com.jinlink.modules.system.service;

import com.jinlink.modules.system.entity.vo.SysDictItemOptionsVO;
import com.jinlink.modules.system.entity.vo.SysDictVO;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysDict;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据字典管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 获取字典Map
     */
    Map<String, List<SysDictItemOptionsVO>> queryAllDictMap();


    /**
     * 获取字典详细信息
     */
    SysDictVO getInfo(Serializable id);

    /**
     * 删除字典多个
     */
    @Transactional
    Boolean removeDictByIds(List<Long> ids);

    /**
     * 删除字典
     */
    @Transactional
    Boolean removeDictById(Serializable id);

    /**
     * 修改字典
     */
    @Transactional
    Boolean updateDictById(SysDict sysDict);
}
