package com.cenxui.tea.app.aws.dynamodb.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.user.User;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ItemUtil {
    public static Item getUserItem(@NonNull User user) {
        final Item item = new Item()
                .withPrimaryKey(User.MAIL, user.getMail());

        Arrays.asList(user.getClass().getDeclaredFields()).forEach(
                (s) -> {
                    String fieldName = s.getName();
                    if (!User.MAIL.equals(fieldName)) {
                        s.setAccessible(true);
                        try {
                            setItem(user, item, s, fieldName);

                        } catch (IllegalAccessException e) {
                            throw new ItemUtilException();
                        }
                    }
                }
        );

        return item;
    }

    public static Item getProductItem(@NonNull Product product) {

        Item item = new Item()
                .withPrimaryKey(
                        Product.PRODUCT_NAME, product.getProductName(),
                        Product.VERSION, product.getVersion())
                .withString(Product.DETAILS, product.getDetails())
                .withString(Product.INTRODUCTION, product.getIntroduction())
                .withString(Product.SMALL_IMAGE, product.getSmallImage())
                .withString(Product.VIDEO, product.getVideo())
                .with(Product.IMAGES, product.getImages())
                .withDouble(Product.PRICE, product.getPrice())
                .withString(Product.TAG, product.getTag());

        Arrays.asList(product.getClass().getDeclaredFields()).forEach(
                (s)-> {
                    String fieldName = s.getName();
                    if (!Product.PRODUCT_NAME.equals(fieldName) &&
                            !Product.VERSION.equals(fieldName)) {

                        s.setAccessible(true);
                        try {
                            setItem(product, item, s, fieldName);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        return item;
    }

    public static Item getOrderItem(@NonNull Order order) {

        //todo prevent null

        Item item = new Item()
                .withPrimaryKey(Order.MAIL, order.getMail(), Order.TIME, order.getTime());

        Arrays.asList(order.getClass().getDeclaredFields()).forEach(
                (s)-> {
                    String fieldName = s.getName();
                    if(!Order.MAIL.equals(fieldName) && !Order.TIME.equals(fieldName)) {
                        s.setAccessible(true);
                        try {
                            setItem(order, item, s, fieldName);
                        } catch (IllegalAccessException e) {
                            throw new ItemUtilException();
                        }
                    }

                }
        );

        return item;
    }

    private static void setItem(@NonNull Object object, Item item, Field s, String fieldName) throws IllegalAccessException {
        if (Modifier.isStatic(s.getModifiers())) {
            return;
        }

        Object field = s.get(object);
        if (field instanceof String && ((String) field).length() != 0) {
            item.withString(fieldName, (String) field);
        }else if (field instanceof Boolean && Boolean.TRUE.equals(field)) {
            item.withBoolean(fieldName, true); //todo
        }else if (field instanceof Number) {
            item.withNumber(fieldName, (Number) field);
        }
    }
}
