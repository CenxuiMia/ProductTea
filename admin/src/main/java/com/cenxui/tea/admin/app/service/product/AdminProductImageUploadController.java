package com.cenxui.tea.admin.app.service.product;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cenxui.tea.admin.app.config.S3Bucket;
import com.cenxui.tea.admin.app.service.AdminCoreController;
import com.cenxui.tea.app.images.ProductImage;
import com.cenxui.tea.app.util.ApplicationError;
import com.cenxui.tea.app.util.JsonUtil;
import org.omg.CORBA.portable.ApplicationException;
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
                S3Bucket.BUCKET_NAME,
                S3Bucket.FOLDER + "/" +
                        image.getProductName() + "/" + image.getVersion() + "/" +
                        image.getFileName() + ".png", file));

        return "success";
    };

    public static final Route putProductImage = (Request request, Response response) -> {



        try {
            AmazonS3 s3client =  AmazonS3ClientBuilder.defaultClient();

            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(Long.valueOf(request.headers("Content-Length")));

            s3client.putObject(new PutObjectRequest(
                    S3Bucket.BUCKET_NAME,
                    S3Bucket.FOLDER + "/" + request.headers("productName") + "/" + request.headers("version") + "/" + request.headers("fileName" )+ ".png",
                    request.raw().getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead));

            return "success";
        }catch (Throwable e) {

            return ApplicationError.getTrace(e.getStackTrace());
        }

    };

}
