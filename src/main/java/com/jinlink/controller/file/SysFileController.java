package com.jinlink.controller.file;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.jinlink.common.api.Result;
import com.jinlink.common.util.file.FileUtils;
import com.jinlink.modules.file.entity.SysFile;
import com.jinlink.modules.file.service.SysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件请求处理
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "文件管理")
@RequiredArgsConstructor
@RequestMapping("/file")
public class SysFileController {

    @NonNull
    private SysFileService sysFileService;

    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    @Operation(operationId = "1", summary = "文件上传")
    @SaCheckLogin
    public Result<SysFile> upload(MultipartFile file) {
        // 上传并返回访问地址
        String url = sysFileService.uploadFile(file);
        SysFile sysFile = new SysFile();
        sysFile.setName(FileUtils.getName(url));
        sysFile.setUrl(url);
        return Result.success("请求成功",sysFile);
    }

    /**
     * 模型上传请求
     */
    @PostMapping("/model/upload")
    @Operation(operationId = "2", summary = "模型上传")
    @SaCheckLogin
    public Result<SysFile> uploadModel(MultipartFile file) {
        // 上传并返回访问地址
        String url = sysFileService.uploadModelFile(file);
        SysFile sysFile = new SysFile();
        sysFile.setName(FileUtils.getName(url));
        sysFile.setUrl(url);
        return Result.success("请求成功",sysFile);
    }
}