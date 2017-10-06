package com.cenxui.tea.app.service.catagory;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by huaying on 05/10/2017.
 */
public class ProductDAOTest {
    ProductDAO productDAO;

    @Before
    public void setUp() throws Exception {

        productDAO = ProductDAO.getTestProductDAO(Collections.unmodifiableList(Arrays.asList(
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
        )));
    }

    @Test
    public void getTestProductDAO() throws Exception {
        //TODO
//        assertEquals(productDAO, ProductDAO.getTestProductDAO(Collections.unmodifiableList(Arrays.asList(
//                Product.of(
//                        1,
//                        "black tea" ,
//                        "good tea from mia banana",
//                        "",
//                        "", new ArrayList<>(), Boolean.TRUE, 100, "mia"),
//                Product.of(
//                        2,
//                        "green tea" ,
//                        "standard tea from cenxui banana",
//                        "",
//                        "",
//                        new ArrayList<>(), Boolean.TRUE, 100, "cenxui"),
//                Product.of(
//                        3,
//                        "woolong tea" ,
//                        "woolong tea from cenxui mia",
//                        "",
//                        "",
//                        new ArrayList<>(), Boolean.TRUE, 200, "cenxui"),
//                Product.of(
//                        4,
//                        "mountain green tea" ,
//                        "mountain tea from cenxui mia",
//                        "",
//                        "",
//                        new ArrayList<>(), Boolean.TRUE, 200, "mia")))));
    }

    @Test
    public void getProductByTag() throws Exception {
        assertEquals(productDAO.getProductByTag("cenxui"), ProductDAO.getProductDAO().getProductByTag("cenxui"));
        assertEquals(null, ProductDAO.getProductDAO().getProductByTag("tag"));
    }

    @Test
    public void getProductById() throws Exception {
        assertEquals(productDAO.getProductById(3), ProductDAO.getProductDAO().getProductById(3));
        assertNotEquals(productDAO.getProductById(1), ProductDAO.getProductDAO().getProductById(3));
        assertEquals(null, ProductDAO.getProductDAO().getProductById(100));
    }

    @Test
    public void getAllProducts() throws Exception {
        List<Product> expectProducts = productDAO.getAllProducts();
        List<Product> actualProducts = ProductDAO.getProductDAO().getAllProducts();
        assertEquals(expectProducts, actualProducts);
    }

    @Test
    public void getProductsByPrice() throws Exception {
        //TODO
//        List<Product> expectProducts = productDAO.getAllProducts();
//        List<Product> actualProducts = ProductDAO.getProductDAO().getAllProducts();
//        for (Product product : expectProducts) {
//            if (product.getPrice() != 200) {
//                expectProducts.remove(product);
//            }
//        }
//
//        for (Product product : actualProducts) {
//            if (product.getPrice() != 200) {
//                actualProducts.remove(product);
//            }
//        }
//        assertEquals(expectProducts, actualProducts);
    }

    @Test
    public void getProductByName() throws Exception {
        assertEquals(productDAO.getProductByName("woolong tea"), ProductDAO.getProductDAO().getProductByName("woolong tea"));
        assertNotEquals(productDAO.getProductByName("woolong tea"), ProductDAO.getProductDAO().getProductByName("abc"));
        assertEquals(null, ProductDAO.getProductDAO().getProductByName("abc"));
    }

}