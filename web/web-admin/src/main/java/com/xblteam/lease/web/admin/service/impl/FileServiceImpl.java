package com.xblteam.lease.web.admin.service.impl;

import com.xblteam.lease.common.minio.MinioConfiguration;
import com.xblteam.lease.web.admin.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfiguration minioConfiguration;

    @Transactional
    @Override
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        //获取bucketName
        String bucketName = minioConfiguration.getBucketName();
        String endpoint = minioConfiguration.getEndpoint();
        String fileName;


        //判断bucket是否存在
        boolean exists = minioClient.bucketExists(BucketExistsArgs
                .builder()
                .bucket(bucketName)
                .build()
        );

        //如果bucket不存在，则创建bucket
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs
                    .builder()
                    .bucket(bucketName)
                    .build());
            //权限字符串
            String policy = """
                    {
                        "Version": "2012-10-17",
                        "Statement": [
                            {
                                "Effect": "Allow",
                                "Principal": {
                                    "AWS": [
                                        "*"
                                    ]
                                },
                                "Action": [
                                    "s3:GetObject"
                                ],
                                "Resource": [
                                    "arn:aws:s3:::%s/*"
                                ]
                            }
                        ]
                    }
                    """.formatted(bucketName);
            //配置bucket权限
            minioClient.setBucketPolicy(SetBucketPolicyArgs
                    .builder()
                    .bucket(bucketName)
                    .config(policy)
                    .build());
        }

        //重命名文件
        fileName = new SimpleDateFormat("yyyyMMdd").format(new Date())
                + "-"
                + UUID.randomUUID()
                + "/"
                + file.getOriginalFilename();

        //上传文件
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucketName)
                .contentType(file.getContentType())                             //设置文件类型
                .stream(file.getInputStream(), file.getSize(), -1)
                .object(fileName)
                .build());


        return endpoint + "/" + bucketName + "/" + fileName;
    }
}
