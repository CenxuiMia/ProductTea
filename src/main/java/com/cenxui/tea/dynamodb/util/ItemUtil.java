package com.cenxui.tea.dynamodb.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.cenxui.tea.app.integration.repositories.catagory.Product;
import com.cenxui.tea.app.integration.repositories.order.Order;
import com.cenxui.tea.dynamodb.util.exception.DuplicateProductException;
import lombok.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemUtil {
    public static Item getProductItem(@NonNull Product product) {

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

    public static Item getOrderItem(@NonNull Order order) throws DuplicateProductException {
        Set<String> productSet = new HashSet<>();
        for (Map<Product, Integer> product : order.getProducts()) {
            if (productSet.contains(product)){
                throw new DuplicateProductException();
            }

            productSet.add(product.toString());
        }


        Item item = new Item()
                .withPrimaryKey(Order.TIME, order.getTime(), Order.MAIL, order.getMail())
                .withStringSet(Order.PRODUCTS, productSet)
                .withString(Order.PURCHASER, order.getPurchaser())
                .withString(Order.PHONE, order.getPhone())
                .withString(Order.ADDRESS, order.getAddress())
                .withString(Order.COMMENTS, order.getComments());

        return item;
    }
}
