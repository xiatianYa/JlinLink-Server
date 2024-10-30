package com.jinlink.common.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 下载工具类
 */

@Slf4j
public class DownloadUtil {

    private DownloadUtil() {

    }

    /**
     * 下载文件
     *
     * @param response 响应
     * @param data     字节数据
     * @param fileName 文件名
     * @author summer
     */
    public static void binary(HttpServletResponse response, byte[] data, String fileName) {
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.addHeader("Content-Length", String.valueOf(data.length));
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("下载文件失败：", e);
        }
    }
}
