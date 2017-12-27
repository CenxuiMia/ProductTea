package com.cenxui.shop.admin.app.util;

import com.cenxui.shop.repositories.product.Product;
import com.cenxui.shop.repositories.product.ProductRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class ProductManagerUtil {
    public static void mockProductList(ProductRepository manager,
                                       String productListName, List<Product> mockProducts)
    throws Exception {
        Field field = manager.getClass().getDeclaredField(productListName);

        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(manager, mockProducts);

    }
}
