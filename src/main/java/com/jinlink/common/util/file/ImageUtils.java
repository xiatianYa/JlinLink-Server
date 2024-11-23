package com.jinlink.common.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 图片处理工具类
 *
 * @author ruoyi
 */
public class ImageUtils {

    // 下载网络图片 获取byte[]
    public static String downloadImageAsResource(String imageUrl, String targetDir, String fileName) {
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
                    Path path = Paths.get(targetDir, fileName);
                    Files.createDirectories(path.getParent());
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                    return "https://www.bluearchive.top/statics/live/" + fileName;
                }
            } else {
                throw new RuntimeException("Failed to download image: " + responseEntity.getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error downloading image", e);
        }
    }
}
