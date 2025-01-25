package com.jinlink.common.util.file;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具类
 *
 * @author ruoyi
 */
public class ImageUtils {

    // 下载网络图片 获取byte[]
    public static String downloadImageAsResource(String fileName,String accessKey,String secretKey,String bucket,String imageUrl) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Resource> responseEntity = restTemplate.exchange(
                    imageUrl,
                    HttpMethod.GET,
                    null,
                    Resource.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                try (InputStream inputStream = responseEntity.getBody().getInputStream()) {
                    return QiniuUtils.uploadFile(fileName, accessKey, secretKey, bucket, inputStream);
                }
            } else {
                throw new RuntimeException("Failed to download image: " + responseEntity.getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error downloading image", e);
        }
    }
}
