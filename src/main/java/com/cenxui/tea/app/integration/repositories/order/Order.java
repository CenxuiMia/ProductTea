package com.cenxui.tea.app.integration.repositories.order;

import com.cenxui.tea.app.integration.repositories.catagory.Product;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class Order {
    Integer id;
    List<Map<Product, Integer>> products;
    String purchaser;
    String phoneNumber;
    String address;
    String comments;
}
