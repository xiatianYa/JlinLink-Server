package com.jinlink.common.listener;

import cn.dev33.satoken.stp.StpUtil;
import com.jinlink.common.domain.BaseEntity;
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
        baseEntity.setUpdateUserId(StpUtil.getLoginIdAsLong());
    }
}
