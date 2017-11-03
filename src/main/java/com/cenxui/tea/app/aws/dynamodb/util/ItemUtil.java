package com.cenxui.tea.app.aws.dynamodb.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.aws.dynamodb.util.exception.DuplicateProductException;
import lombok.NonNull;

import java.util.HashSet;
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
                .withDouble(Product.PRICE, product.getPrice())
                .withString(Product.TAG, product.getTag());

        if ( product.getStatus() == true) {
            item.withBoolean(Product.STATUS, true);
        }

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
                .withPrimaryKey(Order.MAIL, order.getMail(), Order.TIME, order.getTime())
                .withStringSet(Order.PRODUCTS, productSet)
                .withString(Order.PURCHASER, order.getPurchaser())
                .withString(Order.PHONE, order.getPhone())
                .withString(Order.ADDRESS, order.getAddress())
                .withString(Order.COMMENTS, order.getComments());

        if (order.getIsPaid() == true) {
            item.withBoolean(Order.IS_PAID, true);
        }

        if (order.getIsActive() == true) {
            item.withBoolean(Order.IS_ACTIVE, true);
        }

        return item;
    }
}
