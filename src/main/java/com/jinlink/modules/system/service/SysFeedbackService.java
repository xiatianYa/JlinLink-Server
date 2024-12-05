package com.jinlink.modules.system.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysFeedbackAddDTO;
import com.jinlink.modules.system.entity.dto.SysFeedbackSearchDTO;
import com.jinlink.modules.system.entity.dto.SysFeedbackUpdateDTO;
import com.jinlink.modules.system.entity.dto.SysUserSearchDTO;
import com.jinlink.modules.system.entity.vo.SysFeedbackVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysFeedback;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 意见反馈表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysFeedbackService extends IService<SysFeedback> {

    /**
     * 添加意见反馈表。
     */
    @Transactional
    Boolean saveSysFeedback(SysFeedbackAddDTO sysFeedbackAddDTO);

    /**
     * 根据主键更新意见反馈表。
     */
    @Transactional
    Boolean updateSysFeedbackUpdateById(SysFeedbackUpdateDTO sysFeedbackUpdateDTO);

    /**
     * 查询所有意见反馈表。
     */
    List<SysFeedbackVo> listSysFeedbackVo();


    /**
     * 根据意见反馈表主键获取详细信息。
     */
    SysFeedbackVo getSysFeedbackVoById(Serializable id);

    /**
     * 分页查询意见反馈表。
     */
    Page<SysFeedbackVo> pageSysFeedbackVo(PageQuery query, SysFeedbackSearchDTO sysFeedbackSearchDTO);
}
