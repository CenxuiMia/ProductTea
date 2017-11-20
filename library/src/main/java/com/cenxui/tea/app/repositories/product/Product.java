package com.cenxui.tea.app.repositories.product;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Product {
    public static final String NAME = "name";
    public static final String VERSION = "version";
    public static final String DETAILS = "details";
    public static final String SMALL_IMAGE = "smallImage";
    public static final String BIG_IMAGE = "bigImage";
    public static final String IMAGES = "images";
    public static final String STATUS = "status";
    public static final String PRICE = "price";
    public static final String TAG = "tag";


    String name;
    String version;
    String details;
    String smallImage;
    String bigImage;
    List<String> images;
    Boolean status;
    Float price;
    String tag;

}
