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
    private ProductRepositoryManager productRepositoryManager;

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

        productRepositoryManager = ProductRepositoryManager.getManager();

        products =  Collections.unmodifiableList(Arrays.asList(p1, p2, p3, p4));

        ProductManagerUtil.mockProductList(productRepositoryManager, "products", products);
    }

    @Test
    public void getProductsByTag() throws Exception {
        List<Product> cenxuiTag = Arrays.asList(p2, p3);
        assertEquals(productRepositoryManager.getProductsByTag("cenxui"), cenxuiTag);
        assertEquals(Collections.emptyList(), ProductRepositoryManager.getManager().getProductsByTag("tag"));
    }

    @Test
    public void getProductById() throws Exception {
        assertEquals(p3, productRepositoryManager.getProductById(3));
        assertNotEquals(p3, productRepositoryManager.getProductById(1));
        assertEquals(null, productRepositoryManager.getProductById(100));
    }

    @Test
    public void getAllProducts() throws Exception {
        assertEquals(products,  productRepositoryManager.getAllProducts());
    }

    @Test
    public void getProductsByPrice() throws Exception {
        List<Product> price100 = Arrays.asList(p1, p2);
        List<Product> price200 = Arrays.asList(p3, p4);
        assertEquals(price100, productRepositoryManager.getProductsByPrice(100));
        assertEquals(price200, productRepositoryManager.getProductsByPrice(200));
        assertEquals(Collections.emptyList(), productRepositoryManager.getProductsByPrice(500));
    }

    @Test
    public void getProductsByName() throws Exception {
        List<Product> woolongTeaName = Collections.singletonList(p3);
        assertEquals(woolongTeaName, productRepositoryManager.getProductsByName("woolong tea"));
        assertEquals(Collections.emptyList(), productRepositoryManager.getProductsByName("abc"));
    }

}