package com.cenxui.tea.app.util;

import com.cenxui.tea.app.service.catagory.Product;
import com.cenxui.tea.app.service.catagory.repository.ProductRepositoryManager;

import java.util.List;

public class ProductManagerUtil {
    public static void mockProductList(Class<ProductRepositoryManager> clazz,
                                       String productListName, List<Product> mockProducts)
    throws Exception {
        Util.setStaticFinal(clazz, productListName, mockProducts);

    }
}
