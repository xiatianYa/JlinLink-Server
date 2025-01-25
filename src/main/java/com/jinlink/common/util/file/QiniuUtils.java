package com.jinlink.common.util.file;

import com.google.gson.Gson;
import com.jinlink.common.exception.JinLinkException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class QiniuUtils {

    public static String uploadFile(String fileName,String accessKey,String secretKey,String bucket,MultipartFile file) {
        Configuration cfg = new Configuration();
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        cfg.useHttpsDomains = false;
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.getBytes(),fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        }catch (IOException e){
            throw new JinLinkException("文件上传失败");
        }
    }
    public static String uploadFile(String fileName, String accessKey, String secretKey, String bucket, InputStream file) {
        Configuration cfg = new Configuration();
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        cfg.useHttpsDomains = false;
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.readAllBytes(),fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        }catch (IOException e){
            throw new JinLinkException("文件上传失败");
        }
    }
}
