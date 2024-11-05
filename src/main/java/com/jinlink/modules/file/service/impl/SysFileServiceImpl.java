package com.jinlink.modules.file.service.impl;

import com.jinlink.common.util.file.FileUploadUtils;
import com.jinlink.modules.file.service.SysFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SysFileServiceImpl implements SysFileService {
    /**
     * 资源映射路径 前缀
     */
    @Value("${file.prefix}")
    public String localFilePrefix;

    /**
     * 域名或本机访问地址
     */
    @Value("${file.domain}")
    public String domain;

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.path}")
    private String localFilePath;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String name = FileUploadUtils.upload(localFilePath, file);
        return domain + localFilePrefix + name;
    }
}
