package com.jinlink.core.listener;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.InsertListener;

import java.time.LocalDateTime;

/**
 * MybatisFlex自定义字段注入
 */
public class MyInsertListener implements InsertListener {
    @Override
    public void onInsert(Object entity) {
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setCreateTime(LocalDateTime.now());
        baseEntity.setUpdateTime(LocalDateTime.now());
        try {
            if (ObjectUtil.isNull(baseEntity.getCreateUserId())){
                baseEntity.setCreateUserId(StpUtil.getLoginIdAsLong());
            }
            if (ObjectUtil.isNull(baseEntity.getUpdateUserId())){
                baseEntity.setUpdateUserId(StpUtil.getLoginIdAsLong());
            }
        }catch (Exception e){
            baseEntity.setUpdateUserId(0L);
            baseEntity.setCreateUserId(0L);
        }
    }
}
