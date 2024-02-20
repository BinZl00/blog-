package com.binbin.weblog.admin.service.impl;

import com.binbin.weblog.admin.model.vo.file.UploadFileRspVO;
import com.binbin.weblog.admin.utils.MinioUtil;
import com.binbin.weblog.common.enums.ResponseCodeEnum;
import com.binbin.weblog.common.exception.BizException;
import com.binbin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AdminFileServiceImpl implements AdminFileService {

    @Autowired
    private MinioUtil minioUtil;

    /**
     * 上传文件
     */
    @Override
    public Response uploadFile(MultipartFile file) {
        try {
            // 上传文件
            String url = minioUtil.uploadFile(file);
            // 成功将图片的访问链接返回
            return Response.success(UploadFileRspVO.builder().url(url).build());
        } catch (Exception e) {
            log.error("==> 上传文件至 Minio 错误: ", e);
            // 手动抛出业务异常，提示 “文件上传失败”
            throw new BizException(ResponseCodeEnum.FILE_UPLOAD_FAILED);
        }
    }
}