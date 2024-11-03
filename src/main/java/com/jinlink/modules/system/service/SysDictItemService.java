package com.jinlink.modules.system.service;

import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysDictItemSearchDTO;
import com.jinlink.modules.system.entity.vo.SysDictItemVO;
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
    Page<SysDictItemVO> listSysDictItemPage(PageQuery pageQuery, SysDictItemSearchDTO sysDictItemSearchDTO);
}
