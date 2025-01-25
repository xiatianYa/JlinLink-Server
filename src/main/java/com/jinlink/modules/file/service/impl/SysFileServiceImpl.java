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

    @Value("${qiniu.domain}")
    private String domain;

    @Value("${qiniu.basePath}")
    private String basePath;

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Override
    public String uploadFile(MultipartFile file) {
        String fielUrl = "";
        try {
            fielUrl = FileUploadUtils.upload(domain,basePath,accessKey,secretKey,bucket,file);
            initFileLog(file,fielUrl,null);
            return fielUrl;
        }catch (Exception e){
            initFileLog(file,fielUrl,e);
            throw new JinLinkException("文件上传失败"+e.getMessage());
        }
    }

    @Override
    public String uploadModelFile(MultipartFile file) {
        String fielUrl = "";
        try {
            fielUrl = FileUploadUtils.uploadModelFile(domain,basePath,accessKey,secretKey,bucket,file);
            initFileLog(file,fielUrl,null);
            return fielUrl;
        }catch (Exception e){
            initFileLog(file,fielUrl,e);
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
