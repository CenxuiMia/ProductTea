package com.cenxui.tea.app.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Http {
    public static void put(String url, String body) {

        outPut(url, "PUT",body);
    }

    public static void get(String url) {

        BufferedReader reader = null;

        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setUseCaches(false);

            reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            reader.lines().forEach(System.out::println);

        }catch (Exception e) {
            e.printStackTrace();
        }finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void post(String url, String body) {
       outPut(url, "POST", body);
    }

    private static void outPut(String url, String method,String body) {
        OutputStreamWriter writer = null;

        BufferedReader reader = null;

        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setAllowUserInteraction(true);
            connection.setUseCaches(false);

            writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);

            writer.write(body);

            writer.close();

            reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            reader.lines().forEach(System.out::println);

            reader.close();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
