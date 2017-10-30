package com.cenxui.tea.dynamodb.util;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.user.User;

import java.util.HashMap;
import java.util.Map;

public class AttributeValueUtil {
    public static Map<String, AttributeValue> getProductAttributeMap(Product product) {
        Map<String, AttributeValue> map = new HashMap<>();

        map.put(Product.NAME, new AttributeValue().withS(product.getName()));
        map.put(Product.VERSION, new AttributeValue().withN(product.getVersion().toString()));
        map.put(Product.DETAILS, new AttributeValue().withS(product.getDetails()));
        map.put(Product.SMALL_IMAGE, new AttributeValue().withS(product.getSmallImage()));
        map.put(Product.BIG_IMAGE, new AttributeValue().withS(product.getBigImage()));
        map.put(Product.IMAGES, new AttributeValue().withSS(product.getImages()));
        map.put(Product.STATUS, new AttributeValue().withBOOL(product.getStatus()));
        map.put(Product.PRICE, new AttributeValue().withN(product.getPrice().toString()));
        map.put(Product.TAG, new AttributeValue().withS(product.getTag()));

        return map;
    }

    public static Map<String, AttributeValue> getUserAttributeMap(User user) {
        Map<String, AttributeValue> map = new HashMap<>();

        map.put(User.IS_ACTIVE, new AttributeValue().withBOOL(user.getIsActive()));
        map.put(User.USER_NAME, new AttributeValue().withS(user.getUserName()));
        map.put(User.MAIL, new AttributeValue().withS(user.getMail()));
        map.put(User.SALT, new AttributeValue().withS(user.getSalt()));
        map.put(User.HASHED_PASSWORD, new AttributeValue().withS(user.getHashedPassword()));
        map.put(User.ADDRESSES, new AttributeValue().withS(user.getAddresses()));
        map.put(User.PHONE, new AttributeValue().withS(user.getPhone()));
        map.put(User.CELLPHONE, new AttributeValue().withS(user.getCellphone()));
        map.put(User.TOKEN, new AttributeValue().withS(user.getToken()));

        return map;
    }
}
