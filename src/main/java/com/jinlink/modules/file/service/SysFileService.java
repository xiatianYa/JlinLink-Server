package com.jinlink.modules.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 */
public interface SysFileService {
    /**
     * 文件上传接口
     */
    String uploadFile(MultipartFile file) throws Exception;
}
