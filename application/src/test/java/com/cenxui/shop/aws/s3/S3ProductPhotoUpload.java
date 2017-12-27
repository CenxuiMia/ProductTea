package com.cenxui.shop.aws.s3;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.Test;

import java.io.File;

public class S3ProductPhotoUpload {

    @Test
    public void upload() {
        AmazonS3 s3client =  AmazonS3ClientBuilder.defaultClient();

        File file = new File("/Users/cenxui/Downloads/12166549_911034718984522_1989228322_n.jpg");

        s3client.putObject(new PutObjectRequest(
                "cens-s3website-2fkuo2z3mpnh", "image/test2.png", file)
                .withCannedAcl(CannedAccessControlList.PublicRead));




    }


}
