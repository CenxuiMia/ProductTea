package com.cenxui.tea.app.services.catagory;

import com.cenxui.tea.app.repositories.catagory.Product;
import com.cenxui.tea.app.repositories.catagory.ProductRepositoryImpl;
import com.cenxui.tea.app.util.ProductManagerUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by huaying on 05/10/2017.
 */
public class ProductRepositoryImplTest {
    private ProductRepositoryImpl productRepositoryImpl;

    private Product p1 = Product.of(
            1,
            "black tea" ,
            "good tea from mia banana",
            "",
            "", new ArrayList<>(), Boolean.TRUE, 100, "mia");

    private Product p2 = Product.of(
            2,
            "green tea" ,
            "standard tea from cenxui banana",
            "",
            "",
            new ArrayList<>(), Boolean.TRUE, 100, "cenxui");

    private Product p3 = Product.of(
            3,
            "woolong tea" ,
            "woolong tea from cenxui mia",
            "",
            "",
            new ArrayList<>(), Boolean.TRUE, 200, "cenxui");

    private Product p4 = Product.of(
            4,
            "mountain green tea" ,
            "mountain tea from cenxui mia",
            "",
            "",
            new ArrayList<>(), Boolean.TRUE, 200, "mia");

    private List<Product> products =  Collections.unmodifiableList(Arrays.asList(p1, p2, p3, p4));

    @Before
    public void setUp() throws Exception {
        productRepositoryImpl = ProductRepositoryImpl.getManager();
        products =  Collections.unmodifiableList(Arrays.asList(p1, p2, p3, p4));
        ProductManagerUtil.mockProductList(productRepositoryImpl, "products", products);
    }

    @Test
    public void getProductsByTag() throws Exception {
        List<Product> cenxuiTag = Arrays.asList(p2, p3);
        assertEquals(productRepositoryImpl.getProductsByTag("cenxui"), cenxuiTag);
        assertEquals(Collections.emptyList(), ProductRepositoryImpl.getManager().getProductsByTag("tag"));
    }

    @Test
    public void getProductById() throws Exception {
        assertEquals(p3, productRepositoryImpl.getProductById(3));
        assertNotEquals(p3, productRepositoryImpl.getProductById(1));
        assertEquals(null, productRepositoryImpl.getProductById(100));
    }

    @Test
    public void getAllProducts() throws Exception {
        assertEquals(products,  productRepositoryImpl.getAllProducts());
    }

    @Test
    public void getProductsByPrice() throws Exception {
        List<Product> price100 = Arrays.asList(p1, p2);
        List<Product> price200 = Arrays.asList(p3, p4);
        assertEquals(price100, productRepositoryImpl.getProductsByPrice(100));
        assertEquals(price200, productRepositoryImpl.getProductsByPrice(200));
        assertEquals(Collections.emptyList(), productRepositoryImpl.getProductsByPrice(500));
    }

    @Test
    public void getProductsByName() throws Exception {
        List<Product> woolongTeaName = Collections.singletonList(p3);
        assertEquals(woolongTeaName, productRepositoryImpl.getProductsByName("woolong tea"));
        assertEquals(Collections.emptyList(), productRepositoryImpl.getProductsByName("abc"));
    }

}