package com.jinlink.common.config.mybatisFlex;

import com.jinlink.common.domain.BaseEntity;
import com.jinlink.common.listener.MyInsertListener;
import com.jinlink.common.listener.MyUpdateListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisFlexConfiguration {

    private static final Logger logger = LoggerFactory
            .getLogger("mybatis-flex-sql");


    public MyBatisFlexConfiguration() {
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                logger.info("{},{}ms", auditMessage.getFullSql()
                        , auditMessage.getElapsedTime())
        );

        //配置全局属性监听器
        MyInsertListener insertListener = new MyInsertListener();
        MyUpdateListener updateListener = new MyUpdateListener();
        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();
        config.registerInsertListener(insertListener,BaseEntity.class);
        config.registerUpdateListener(updateListener,BaseEntity.class);
    }
}