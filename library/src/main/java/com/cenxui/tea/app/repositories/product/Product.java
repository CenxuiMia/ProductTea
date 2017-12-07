package com.cenxui.tea.app.repositories.product;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Product {
    public static final String PRODUCT_NAME = "productName";
    public static final String VERSION = "version";
    public static final String INTRODUCTION = "introduction";
    public static final String DETAILS = "details";
    public static final String SMALL_IMAGE = "smallImage";
    public static final String VIDEO = "video";
    public static final String IMAGES = "images";
    public static final String PRICE = "price";
    public static final String ORIGINAL_PRICE = "originalPrice";
    public static final String TAG = "tag";


    String productName;
    String version;
    String introduction;
    String details;
    String smallImage;
    String video;
    List<String> images;
    Float price;
    Float originalPrice;
    String tag;

}
