package com.jinlink.modules.monitor.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.file.entity.dto.MonLogsFileSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsFileVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.monitor.entity.MonLogsFile;

/**
 * 文件上传日志 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface MonLogsFileService extends IService<MonLogsFile> {

    /**
     * 分页查询文件上传日志。
     */
    RPage<MonLogsFileVo> listMonLogsFileVoPage(PageQuery pageQuery, MonLogsFileSearchDTO monLogsFileSearchDTO);

    /**
     * 清空文件日志。
     */
    Boolean clearAll();
}
