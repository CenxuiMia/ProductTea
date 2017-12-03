package com.cenxui.tea.app.aws.dynamodb.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.kms.model.UnsupportedOperationException;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.user.User;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Ignore null value
 *
 * only support String Number Boolean bytearray bytebuffer Set Map List
 *
 */

public class ItemUtil {
    public static Item getUserItem(@NonNull User user) {
        final Item item = new Item()
                .withPrimaryKey(User.MAIL, user.getMail());

        Arrays.asList(user.getClass().getDeclaredFields()).forEach(
                (s) -> {
                    String fieldName = s.getName();
                    if (!User.MAIL.equals(fieldName)) {
                        s.setAccessible(true);
                        setItem(user, item, s, fieldName);
                    }
                }
        );

        return item;
    }

    public static Item getProductItem(@NonNull Product product) {

        Item item = new Item()
                .withPrimaryKey(
                        Product.PRODUCT_NAME, product.getProductName(),
                        Product.VERSION, product.getVersion());

        Arrays.asList(product.getClass().getDeclaredFields()).forEach(
                (s)-> {
                    String fieldName = s.getName();
                    if (!Product.PRODUCT_NAME.equals(fieldName) &&
                            !Product.VERSION.equals(fieldName)) {

                        s.setAccessible(true);
                        setItem(product, item, s, fieldName);
                    }
                }
        );

        return item;
    }

    public static Item getOrderItem(@NonNull Order order) {

        Item item = new Item()
                .withPrimaryKey(Order.MAIL, order.getMail(), Order.ORDER_DATE_TIME, order.getOrderDateTime());

        Arrays.asList(order.getClass().getDeclaredFields()).forEach(
                (s)-> {
                    String fieldName = s.getName();
                    if(!Order.MAIL.equals(fieldName) && !Order.ORDER_DATE_TIME.equals(fieldName)) {
                        s.setAccessible(true);
                        setItem(order, item, s, fieldName);

                    }

                }
        );

        return item;
    }

    private static void setItem(@NonNull Object object, Item item, Field s, String fieldName) {
        try {
            if (Modifier.isStatic(s.getModifiers())) {
                return;
            }

            Object field = s.get(object);

            if (field instanceof String) {
                if ( ((String) field).length() != 0) {
                    item.withString(fieldName, (String) field);
                }
            }else if (field != null ) {
                item.with(fieldName, field); // todo modify it to make efficient
            }

        } catch (IllegalAccessException e) {
            throw new ItemUtilCannotAccessFieldException(fieldName);
        } catch (UnsupportedOperationException e) {
            throw new ItemUtilNotSupportClassTypeException(fieldName, s.getType().getTypeName());
        }
    }
}
