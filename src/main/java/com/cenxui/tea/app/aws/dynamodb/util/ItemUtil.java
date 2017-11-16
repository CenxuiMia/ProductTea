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

        if (product.getStatus() == true) {
            item.withBoolean(Product.STATUS, true);
        }

        return item;
    }

    public static Item getOrderItem(@NonNull Order order) throws DuplicateProductException {
        Set<String> productSet = new HashSet<>();
        for (String product : order.getProducts()) {
            if (productSet.contains(product)){
                throw new DuplicateProductException();
            }

            productSet.add(product);
        }


        Item item = new Item()
                .withPrimaryKey(Order.MAIL, order.getMail(), Order.TIME, order.getTime())
                .withStringSet(Order.PRODUCTS, productSet)
                .withString(Order.PURCHASER, order.getPurchaser())
                .withNumber(Order.MONEY, order.getMoney())
                .withString(Order.RECEIVER, order.getReceiver())
                .withString(Order.PHONE, order.getPhone())
                .withString(Order.ADDRESS, order.getAddress())
                .withString(Order.COMMENT, order.getComment());

            if (order.getPaidDate() != null) {
                item.withString(Order.PAID_DATE, order.getPaidDate());
            }

            if (order.getShipDate() != null) {
                item.withString(Order.SHIP_DATE, order.getShipDate());
            }

        if (order.getIsActive() != Boolean.FALSE) {
            item.withBoolean(Order.IS_ACTIVE, true);
        }

        return item;
    }
}
