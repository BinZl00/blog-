package com.binbin.weblog.admin.controller;

import com.binbin.weblog.admin.service.impl.AdminFileService;
import com.binbin.weblog.common.aspect.ApiOperationLog;
import com.binbin.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 文件模块")
public class AdminFileController {

    @Autowired
    private AdminFileService fileService;

    @PostMapping("/file/upload")
    @ApiOperation(value = "文件上传")
    @ApiOperationLog(description = "文件上传")
    //@RequestParam注解在 作用是接收 用户通过表单<input type="file">文件上传
    public Response uploadFile(@RequestParam MultipartFile file) {
        return fileService.uploadFile(file);
    }

}
