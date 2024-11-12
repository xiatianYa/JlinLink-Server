package com.jinlink.modules.system.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysDictItemAddDTO;
import com.jinlink.modules.system.entity.dto.SysDictItemSearchDTO;
import com.jinlink.modules.system.entity.dto.SysDictItemUpdateDTO;
import com.jinlink.modules.system.entity.dto.SysDictUpdateDTO;
import com.jinlink.modules.system.entity.vo.SysDictItemVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysDictItem;

/**
 * 数据字典子项管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysDictItemService extends IService<SysDictItem> {
    Page<SysDictItemVo> listSysDictItemPage(PageQuery pageQuery, SysDictItemSearchDTO sysDictItemSearchDTO);

    /**
     * 添加数据字典子项管理。
     */
    Boolean saveDictItem(SysDictItemAddDTO sysDictItemAddDTO);

    /**
     * 根据主键更新数据字典子项管理。
     */
    Boolean updateDictItem(SysDictItemUpdateDTO sysDictItemUpdateDTO);
}
