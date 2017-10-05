package com.cenxui.tea.app.service.catagory;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        assertEquals(productDAO, ProductDAO.getProductDAO());
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
//        while (productDAO.getAllProducts().iterator().hasNext() &&
//                ProductDAO.getProductDAO().getAllProducts().iterator().hasNext()) {
//            if (!productDAO.getAllProducts().iterator().next().equals(ProductDAO.getProductDAO().getAllProducts().iterator().next())) {
//                throw new Exception("Products not equal!");
//            }
//        }
    }

    @Test
    public void getProductsByPrice() throws Exception {
    }

    @Test
    public void getProductByName() throws Exception {
    }

    @Test
    public void getProductsByPrefix() throws Exception {
    }

}