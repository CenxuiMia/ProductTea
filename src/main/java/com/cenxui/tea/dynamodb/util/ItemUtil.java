package com.cenxui.tea.dynamodb.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.cenxui.tea.app.integration.repositories.catagory.Product;

public class ItemUtil {
    public static Item getProductItem(Product product) {
        Item item = new Item()
                .withPrimaryKey(Product.NAME, product.getName(), Product.VERSION, product.getVersion())
                .withString(Product.DETAILS, product.getDetails())
                .withString(Product.IMAGES, product.getSmallImage())
                .withString(Product.BIG_IMAGE, product.getBigImage())
                .with(Product.IMAGES, product.getImages())
                .withBoolean(Product.STATUS, product.getStatus())
                .withDouble(Product.PRICE, product.getPrice())
                .withString(Product.TAG, product.getTag());

        return item;
    }
}
