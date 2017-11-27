package com.cenxui.tea.app.aws.dynamodb.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.user.User;
import lombok.NonNull;

public class ItemUtil {
    public static Item getUserItem(@NonNull User user) {
        Item item = new Item()
                .withPrimaryKey(User.MAIL, user.getMail());

        if (user.getIsActive() != null) {
            item.withBoolean(User.IS_ACTIVE, user.getIsActive());
        }

        if (user.getAddress() != null) {
            item.withString(User.ADDRESS, user.getAddress());
        }

        if (user.getFirstName() != null) {
            item.withString(User.FIRST_NAME, user.getFirstName());
        }

        if (user.getLastName() != null) {
            item.withString(User.LAST_NAME, user.getLastName());
        }

        if (user.getPhone() != null) {
            item.withString(User.PHONE, user.getPhone());
        }

        if (user.getMail() != null) {
            item.withString(User.MAIL, user.getMail());
        }
        return item;
    }

    public static Item getProductItem(@NonNull Product product) {
        //todo prevent null

        Item item = new Item()
                .withPrimaryKey(Product.PRODUCT_NAME, product.getProductName(), Product.VERSION, product.getVersion())
                .withString(Product.DETAILS, product.getDetails())
                .withString(Product.INTRODUCTION, product.getIntroduction())
                .withString(Product.SMALL_IMAGE, product.getSmallImage())
                .withString(Product.VIDEO, product.getVideo())
                .with(Product.IMAGES, product.getImages())
                .withDouble(Product.PRICE, product.getPrice())
                .withString(Product.TAG, product.getTag());

        return item;
    }

    public static Item getOrderItem(@NonNull Order order) {

        //todo prevent null

        Item item = new Item()
                .withPrimaryKey(Order.MAIL, order.getMail(), Order.TIME, order.getTime())
                .withList(Order.PRODUCTS, order.getProducts())
                .withString(Order.PURCHASER, order.getPurchaser())
                .withNumber(Order.MONEY, order.getMoney())
                .withString(Order.RECEIVER, order.getReceiver())
                .withString(Order.PHONE, order.getPhone())
                .withString(Order.ADDRESS, order.getAddress());

        if (order.getComment() != null) {
            item.withString(Order.COMMENT, order.getComment());
        }

        if (order.getPaidDate() != null) {
            item.withString(Order.PAID_DATE, order.getPaidDate());
        }

        if (order.getShippedDate() != null) {
            item.withString(Order.SHIP_DATE, order.getShippedDate());
        }

        if (order.getProcessingDate() != null) {
            item.withString(Order.PROCESS_DATE, order.getProcessingDate());
        }

        if (order.getIsActive() != Boolean.FALSE) {
            item.withBoolean(Order.IS_ACTIVE, true);
        }

        return item;
    }
}
