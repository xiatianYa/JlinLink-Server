package com.jinlink.core.listener;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.UpdateListener;

import java.time.LocalDateTime;

/**
 * MybatisFlex自定义字段修改
 */
public class MyUpdateListener implements UpdateListener {
    @Override
    public void onUpdate(Object entity) {
        BaseEntity baseEntity = (BaseEntity) entity;
        baseEntity.setUpdateTime(LocalDateTime.now());
        try {
            if (ObjectUtil.isNull(baseEntity.getUpdateUserId())){
                baseEntity.setUpdateUserId(StpUtil.getLoginIdAsLong());
            }
        }catch (Exception e){
            baseEntity.setUpdateUserId(0L);
        }
    }
}
