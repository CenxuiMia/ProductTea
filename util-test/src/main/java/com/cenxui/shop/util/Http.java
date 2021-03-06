package com.cenxui.shop.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Http {

    public static void put(String url, String body) {
        outPut(url, "PUT",body, null);
    }

    public static void put(String url, String body, Map<String, String> headers) {
        outPut(url, "PUT",body, headers);
    }

    public static void get(String url) {

        input(url, "GET", null);
    }

    public static void get(String url, Map<String, String> headers) {

        input(url, "GET", headers);
    }

    public static void delete(String url) {
        input(url, "DELETE", null);
    }


    public static void delete(String url, Map<String, String> headers) {
        input(url, "DELETE", headers);
    }

    public static void post(String url) {
        input(url, "POST", null);
    }

    public static void post(String url, String body) {
       outPut(url, "POST", body, null);
    }

    public static void post(String url, String body, Map<String, String> headers) {
        outPut(url, "POST", body, headers);
    }


    public static String getResult(String url) {
        BufferedReader reader = null;

        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setUseCaches(false);

            reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder builder = new StringBuilder();

            reader.lines().forEach((s)-> {
                builder.append(s);
            });
            return builder.toString();
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
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


    private static void outPut(String url, String method,String body, Map<String, String> headers) {
        OutputStreamWriter writer = null;

        BufferedReader reader = null;

        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setAllowUserInteraction(true);
            connection.setUseCaches(false);

            if (headers != null) {
                for (String header : headers.keySet()) {
                    connection.setRequestProperty(header, headers.get(header));
                }
            }


            writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);

            writer.write(body);

            writer.close();


            if (connection.getResponseCode() > 300) {
                reader = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }else {
                reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            }

            reader.lines().forEach(System.out::println);
            reader.close();
            System.out.println(connection.getHeaderFields());

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



    private static void input(String url, String method, Map<String, String> headers) {
        BufferedReader reader = null;

        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            if (headers != null) {
                for (String header : headers.keySet()) {
                    connection.setRequestProperty(header, headers.get(header));
                }
            }


            if (connection.getResponseCode() > 300) {
                reader = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }else {
                reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            }

            reader.lines().forEach(System.out::println);
            reader.close();

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


}
