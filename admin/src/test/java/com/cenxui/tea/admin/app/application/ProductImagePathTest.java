package com.cenxui.tea.admin.app.application;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.cenxui.tea.app.util.Http;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductImagePathTest {

    private String url = "http://localhost:9000/admin/product/image";

    @Test
    public void upload() throws Exception{
        long time = System.currentTimeMillis();
        URL urls = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setAllowUserInteraction(true);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("productName", "ice");
        connection.setRequestProperty("version", "little");
        connection.setRequestProperty("fileName", "smallImage2");

//        BufferedInputStream in
//                = new BufferedInputStream(new FileInputStream("/Users/cenxui/Downloads/smallImage.png"));
//
//        BufferedOutputStream out
//                = new BufferedOutputStream(connection.getOutputStream());
//
//        while (in.available()>0) {
//            out.write(in.read());
//        }

        FileInputStream in = new FileInputStream("/Users/cenxui/Downloads/smallImage.png");

        OutputStream out = connection.getOutputStream();

        int read = 0;
        while (read != -1) {
            read = in.read();
            out.write(read);
        }

        System.out.println(System.currentTimeMillis() - time);
        in.close();
        out.close();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), java.nio.charset.StandardCharsets.UTF_8));
        reader.lines().forEach(System.out::println);

    }

    @Test
    public void deletProductImage() {
        Http.delete(url + "/test/remove");
    }

}
