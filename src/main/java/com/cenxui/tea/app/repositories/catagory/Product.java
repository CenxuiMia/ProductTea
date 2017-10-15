package com.cenxui.tea.app.repositories.catagory;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Product {

    Integer id;
    String name;
    String details;
    String smallImage;
    String bigImage;
    List<String> images;
    Boolean status;
    Integer price;
    String tag;
}
