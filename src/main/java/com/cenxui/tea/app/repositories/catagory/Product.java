package com.cenxui.tea.app.repositories.catagory;

import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Integer version;
    String details;
    String smallImage;
    String bigImage;
    List<String> images;
    Boolean status;
    Double price;
    String tag;

}
