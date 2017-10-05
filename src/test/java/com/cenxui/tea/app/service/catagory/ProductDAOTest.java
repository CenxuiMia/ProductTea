package com.cenxui.tea.app.service.catagory;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
                        new ArrayList<>(), Boolean.TRUE, 100, "cenxui")
        )));
    }

    @Test
    public void getTestProductDAO() throws Exception {
//        assertArrayEquals();
    }

    @Test
    public void getProductByTag() throws Exception {
    }

    @Test
    public void getProductById() throws Exception {
    }

    @Test
    public void getAllProducts() throws Exception {
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