package com.jinlink.modules.monitor.service;


import com.jinlink.modules.monitor.entity.vo.MonCacheRedisVo;

/**
 * 缓存服务监控 门面接口层
 */
public interface IMonCacheFacade {

    /**
     * 获取 Redis 信息
     */
    MonCacheRedisVo redisInfo();
}
