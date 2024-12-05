package com.jinlink.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.SysUser;
import com.jinlink.modules.system.entity.dto.SysFeedbackAddDTO;
import com.jinlink.modules.system.entity.dto.SysFeedbackUpdateDTO;
import com.jinlink.modules.system.entity.vo.SysFeedbackVo;
import com.jinlink.modules.system.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysFeedback;
import com.jinlink.modules.system.mapper.SysFeedbackMapper;
import com.jinlink.modules.system.service.SysFeedbackService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 意见反馈表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class SysFeedbackServiceImpl extends ServiceImpl<SysFeedbackMapper, SysFeedback> implements SysFeedbackService {
    @Resource
    private SysFeedbackMapper sysFeedbackMapper;
    @Resource
    private SysUserService sysUserService;

    /**
     * 添加意见反馈表。
     */
    @Override
    public Boolean saveSysFeedback(SysFeedbackAddDTO sysFeedbackAddDTO) {
        SysFeedback sysFeedback = BeanUtil.copyProperties(sysFeedbackAddDTO, SysFeedback.class,"mapUrls");
        sysFeedback.setImage(Arrays.toString(sysFeedbackAddDTO.getImage().split(",")));
        return save(sysFeedback);
    }

    /**
     * 根据主键更新意见反馈表。
     */
    @Override
    public Boolean updateSysFeedbackUpdateById(SysFeedbackUpdateDTO sysFeedbackUpdateDTO) {
        SysFeedback sysFeedback = BeanUtil.copyProperties(sysFeedbackUpdateDTO, SysFeedback.class,"mapUrls");
        sysFeedback.setImage(Arrays.toString(sysFeedbackUpdateDTO.getImage().split(",")));
        return updateById(sysFeedback);
    }

    /**
     * 查询所有意见反馈表。
     */
    @Override
    public List<SysFeedbackVo> listSysFeedbackVo() {
        List<SysFeedback> sysFeedbackList = sysFeedbackMapper.selectAll();
        List<SysFeedbackVo> sysFeedbackVoList = new ArrayList<>();
        sysFeedbackList.forEach(sysFeedback -> {
            SysFeedbackVo sysFeedbackVo = BeanUtil.copyProperties(sysFeedback,SysFeedbackVo.class,"mapUrls");
            sysFeedback.setImage(String.join(",", sysFeedback.getImage()));
            sysFeedbackVoList.add(sysFeedbackVo);
        });
        return sysFeedbackVoList;
    }


    /**
     * 根据意见反馈表主键获取详细信息。
     */
    @Override
    public SysFeedbackVo getSysFeedbackVoById(Serializable id) {
        SysFeedback sysFeedback = sysFeedbackMapper.selectOneById(id);
        SysFeedbackVo sysFeedbackVo = BeanUtil.copyProperties(sysFeedback,SysFeedbackVo.class,"mapUrls");
        sysFeedback.setImage(String.join(",", sysFeedback.getImage()));
        return sysFeedbackVo;
    }

    /**
     * 分页查询意见反馈表。
     */
    @Override
    public Page<SysFeedbackVo> pageSysFeedbackVo(PageQuery query) {
        Page<SysFeedback> paginate = sysFeedbackMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper());
        List<SysFeedback> records = paginate.getRecords();
        List<SysFeedbackVo> sysFeedbackVoList = new ArrayList<>();
        List<SysUser> sysUsers = sysUserService.list(new QueryWrapper());
        records.forEach(sysFeedback -> {
            SysFeedbackVo sysFeedbackVo = BeanUtil.copyProperties(sysFeedback,SysFeedbackVo.class,"mapUrls");
            sysFeedback.setImage(String.join(",", sysFeedback.getImage()));
            //查找用户昵称
            Optional<SysUser> userOptional = sysUsers.stream().filter(item -> item.getId()
                    .equals(sysFeedback.getCreateUserId())).findFirst();
            if (userOptional.isPresent()){
                sysFeedbackVo.setUserName(userOptional.get().getNickName());
            }else{
                sysFeedbackVo.setUserName("无用户名称!");
            }
            sysFeedbackVoList.add(sysFeedbackVo);
        });
        return new Page<>(sysFeedbackVoList,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow());
    }
}
