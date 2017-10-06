package com.cenxui.tea.app.service.catagory;

import com.cenxui.tea.app.service.catagory.repository.ProductRepositoryManager;
import com.cenxui.tea.app.util.ProductManagerUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by huaying on 05/10/2017.
 */
public class ProductRepositoryManagerTest {
    ProductRepositoryManager productRepositoryManager;

    @Before
    public void setUp() throws Exception {

        List<Product> products =  Collections.unmodifiableList(Arrays.asList(
                Product.of(
                        1,
                        "black tea" ,
                        "good tea from mia banana",
                        "",
                        "", new ArrayList<>(), Boolean.TRUE, 100, "mia"),
                Product.of(
                        2,
                        "green tea" ,
                        "standard tea from cenxui banana",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 100, "cenxui"),
                Product.of(
                        3,
                        "woolong tea" ,
                        "woolong tea from cenxui mia",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 200, "cenxui"),
                Product.of(
                        4,
                        "mountain green tea" ,
                        "mountain tea from cenxui mia",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 200, "mia")
        ));

        ProductManagerUtil.mockProductList(ProductRepositoryManager.class, "products", products);

        productRepositoryManager = ProductRepositoryManager.getManager();
    }

    @Test
    public void getProductByTag() throws Exception {
        assertEquals(productRepositoryManager.getProductsByTag("cenxui"), );
        assertEquals(null, ProductRepositoryManager.getProductRepositoryManager().getProductByTag("tag"));
    }

    @Test
    public void getProductById() throws Exception {
        assertEquals(productRepositoryManager.getProductById(3), ProductRepositoryManager.getProductRepositoryManager().getProductById(3));
        assertNotEquals(productRepositoryManager.getProductById(1), ProductRepositoryManager.getProductRepositoryManager().getProductById(3));
        assertEquals(null, ProductRepositoryManager.getProductRepositoryManager().getProductById(100));
    }

    @Test
    public void getAllProducts() throws Exception {
        List<Product> expectProducts = productRepositoryManager.getAllProducts();
        List<Product> actualProducts = ProductRepositoryManager.getProductRepositoryManager().getAllProducts();
        assertEquals(expectProducts, actualProducts);
    }

    @Test
    public void getProductsByPrice() throws Exception {
        //TODO
    }

    @Test
    public void getProductByName() throws Exception {
        assertEquals(productRepositoryManager.getProductByName("woolong tea"), ProductRepositoryManager.getProductRepositoryManager().getProductByName("woolong tea"));
        assertNotEquals(productRepositoryManager.getProductByName("woolong tea"), ProductRepositoryManager.getProductRepositoryManager().getProductByName("abc"));
        assertEquals(null, ProductRepositoryManager.getProductRepositoryManager().getProductByName("abc"));
    }

}