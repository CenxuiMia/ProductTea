package com.cenxui.tea.admin.app.service.product;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cenxui.tea.admin.app.config.S3Bucket;
import com.cenxui.tea.admin.app.service.AdminCoreController;
import com.cenxui.tea.app.image.ProductImage;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.File;

public class AdminProductImageUploadController extends AdminCoreController {

    public static final Route uploadProductImage = (Request request, Response response) -> {
        String body = request.body();

        ProductImage image = JsonUtil.mapToProductImage(body);

        AmazonS3 s3client =  AmazonS3ClientBuilder.defaultClient();

        File file = new File(image.getFilePath());

        s3client.putObject(new PutObjectRequest(
                S3Bucket.BUCKET_NAME, S3Bucket.FOLDER + "/" + file.getName(), file));

        return "success";
    };

}
