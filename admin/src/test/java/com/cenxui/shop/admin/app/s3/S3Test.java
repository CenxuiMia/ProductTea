package com.cenxui.shop.admin.app.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.cenxui.shop.admin.app.config.AWSS3Bucket;
import org.junit.Test;

public class S3Test {

    @Test
    public void deleteObject() {
        AmazonS3 s3client =  AmazonS3ClientBuilder.defaultClient();


        for (S3ObjectSummary file : s3client.listObjects(AWSS3Bucket.BUCKET_NAME, "image/test/remove").getObjectSummaries()){
            s3client.deleteObject(AWSS3Bucket.BUCKET_NAME, file.getKey());
        }

    }

}
