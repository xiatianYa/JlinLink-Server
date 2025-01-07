package com.jinlink.modules.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 */
public interface SysFileService {
    /**
     * 文件上传接口
     */
    String uploadFile(MultipartFile file);


    /**
     * 模型上传请求
     */
    String uploadModelFile(MultipartFile file);
}
