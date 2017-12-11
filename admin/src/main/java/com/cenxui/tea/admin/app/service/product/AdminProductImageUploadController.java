package com.cenxui.tea.admin.app.service.product;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cenxui.tea.admin.app.config.S3Bucket;
import com.cenxui.tea.admin.app.service.AdminCoreController;
import com.cenxui.tea.admin.app.util.LimitedSizeInputStream;
import com.cenxui.tea.admin.app.util.Param;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.Map;

public class AdminProductImageUploadController extends AdminCoreController {

    private static final long limitSize = 50_000;

    public static final Route putProductImage = (Request request, Response response) -> {

        Map<String, String> map = request.params();

        String productName = getProductName(map);
        String version = getVersion(map);
        String fileName = getFileName(map);

        try {

            Long contentLength = Long.valueOf(request.headers("Content-Length"));

            if (contentLength > limitSize)
                throw new AdminProductImageUploadControllerClientException("content size over limit " + limitSize);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(contentLength);

            LimitedSizeInputStream inputStream = new LimitedSizeInputStream(
                    request.raw().getInputStream(),
                    limitSize
            );


            AmazonS3 s3client =  AmazonS3ClientBuilder.defaultClient();
            s3client.putObject(new PutObjectRequest(
                    S3Bucket.BUCKET_NAME,
                    S3Bucket.FOLDER + "/" + productName + "/" + version + "/" + fileName,
                    inputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));

            return "success"; //todo
        }catch (NumberFormatException e) {
            throw new AdminProductImageUploadControllerClientException("Content-Length header not integer");
        }catch (IOException e) {
            throw new AdminProductControllerClientException(e.getMessage());
        }
    };

    private static String getProductName(Map<String, String> map) {
        return map.get(Param.PRODUCT_IMAGE_PRODUCT_NAME);
    }

    private static String getVersion(Map<String, String> map) {
        return map.get(Param.PRODUCT_IMAGE_VERSION);
    }

    private static String getFileName(Map<String, String> map) {
        return map.get(Param.PRODUCT_IMAGE_FILENAME);
    }
}
