package com.binbin.weblog.admin.utils;

import com.binbin.weblog.admin.config.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@Slf4j
public class MinioUtil {//Minio上传文件

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;  //Minio客户端，用于与Minio服务进行交互

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file) throws Exception {
        // 判断文件是否为空
        if (file == null || file.getSize() == 0) {
            log.error("==> 上传文件异常：文件大小为空 ...");
            throw new RuntimeException("文件大小不能为空");
        }
        // 文件的原始名称
        String originalFileName = file.getOriginalFilename();
        // 文件的 Content-Type
        String contentType = file.getContentType();

        // 生成存储对象的名称（将 UUID 字符串中的 - 替换成空字符串）
        String key = UUID.randomUUID().toString().replace("-", "");
        // 获取文件的后缀，如 .jpg       substring()方法截取字符串
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        //拼接xxxx.xx,存储的文件名，format()生成一个新的字符串, key和suffix是两个字符串入参,插入 %s 占位符位置。
        String objectName = String.format("%s%s", key, suffix);
        log.info("==> 开始上传文件到 Minio, ObjectName: {}", objectName);

        //Minio 客户端库将文件上传到 Minio 对象存储服务
        minioClient.putObject(  PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1) //文件内容不是一次性全部读取到内存中，而是边读边上传
                .contentType(contentType)
                .build());

        // 返回文件的访问链接
        String url = String.format("%s/%s/%s", minioProperties.getEndpoint(), minioProperties.getBucketName(), objectName);
        log.info("==> 上传文件至 Minio 成功，访问路径: {}", url);
        return url;
    }
}
