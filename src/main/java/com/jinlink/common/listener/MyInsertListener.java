package com.jinlink.common.listener;

import cn.dev33.satoken.stp.StpUtil;
import com.jinlink.common.domain.BaseEntity;
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
        baseEntity.setUpdateUserId(StpUtil.getLoginIdAsLong());
        baseEntity.setCreateUserId(StpUtil.getLoginIdAsLong());
    }
}
