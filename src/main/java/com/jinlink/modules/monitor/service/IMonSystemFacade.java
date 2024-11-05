package com.jinlink.modules.monitor.service;


import com.jinlink.modules.monitor.entity.vo.MonSystemVo;

/**
 * 系统服务监控 门面接口层
 */
public interface IMonSystemFacade {

    /**
     * 获取服务器信息
     */
    MonSystemVo getServerInfo();
}
