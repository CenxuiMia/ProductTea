package com.cenxui.tea.app.service.catagory;

import lombok.Value;

import java.util.List;

@Value
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
