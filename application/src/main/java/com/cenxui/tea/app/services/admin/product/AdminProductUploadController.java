package com.cenxui.tea.app.services.admin.product;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cenxui.tea.app.services.CoreController;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.File;

public class AdminProductUploadController extends CoreController {

    public static final Route uploadProductImage = (Request request, Response response) -> {
        String body = request.body();

        AmazonS3 s3client =  AmazonS3ClientBuilder.defaultClient();

        File file = new File("/Users/cenxui/Downloads/12166549_911034718984522_1989228322_n.jpg");

        s3client.putObject(new PutObjectRequest(
                "product.hwangying.com", file.getName(), file));

        //todo

        return "upload image";
    };

}
