package com.cenxui.tea.app.util;

import com.cenxui.tea.app.service.catagory.Product;
import com.cenxui.tea.app.service.catagory.repository.ProductRepositoryManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class ProductManagerUtil {
    public static void mockProductList(ProductRepositoryManager manager,
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
