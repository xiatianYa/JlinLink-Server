package com.jinlink.modules.file.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.domain.LoginUser;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.util.file.FileUploadUtils;
import com.jinlink.common.util.file.FileUtils;
import com.jinlink.core.holder.GlobalUserHolder;
import com.jinlink.modules.file.service.SysFileService;
import com.jinlink.modules.monitor.entity.MonLogsFile;
import com.jinlink.modules.monitor.service.MonLogsFileService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SysFileServiceImpl implements SysFileService {
    @Resource
    private MonLogsFileService monLogsFileService;

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
    public String uploadFile(MultipartFile file) {
        String fielUrl = "";
        try {
            fielUrl = FileUploadUtils.upload(localFilePath, file);
            initFileLog(file,domain + localFilePrefix + fielUrl,null);
            return domain + localFilePrefix + fielUrl;
        }catch (Exception e){
            initFileLog(file,domain + localFilePrefix + fielUrl,e);
            throw new JinLinkException("文件上传失败"+e.getMessage());
        }
    }

    @Override
    public String uploadModelFile(MultipartFile file) {
        String fielUrl = "";
        try {
            fielUrl = FileUploadUtils.uploadModelFile(localFilePath, file);
            initFileLog(file,domain + localFilePrefix + fielUrl,null);
            return domain + localFilePrefix + fielUrl;
        }catch (Exception e){
            initFileLog(file,domain + localFilePrefix + fielUrl,e);
            throw new JinLinkException("文件上传失败"+e.getMessage());
        }
    }

    private void initFileLog(MultipartFile file,String fileUrl,Exception e) {
        LoginUser loginUser = GlobalUserHolder.getUser();
        MonLogsFile monLogsFile;
        if (ObjectUtil.isNull(e)){
            monLogsFile = MonLogsFile.builder()
                    .userId(loginUser.getId())
                    .userName(loginUser.getUserName())
                    .fileSize(FileUtils.getFileSizeInMB(file))
                    .fileUrl(fileUrl)
                    .status("1")
                    .build();
        }else{
            monLogsFile = MonLogsFile.builder()
                    .userId(loginUser.getId())
                    .userName(loginUser.getUserName())
                    .fileSize(FileUtils.getFileSizeInMB(file))
                    .fileUrl(fileUrl)
                    .status("0")
                    .build();
        }
        monLogsFileService.save(monLogsFile);
    }
}
