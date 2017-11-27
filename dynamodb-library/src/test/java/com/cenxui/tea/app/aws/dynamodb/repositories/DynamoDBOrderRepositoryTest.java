package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.repositories.order.OrderRepository;
import org.junit.Test;

import static org.junit.Assert.*;

public class DynamoDBOrderRepositoryTest {
    private OrderRepository orderRepository = DynamoDBRepositoryService
            .getOrderRepository(
                    Config.REGION,
                    Config.ORDER_TABLE,
                    Config.ORDER_PAID_INDEX,
                    Config.ORDER_PROCESSING_INDEX,
                    Config.ORDER_SHIPPED_INDEX,
                    Config.PRODUCT_TABLE);
    @Test
    public void getAllOrdersByLastKey() throws Exception {
    }

    @Test
    public void getAllProcessingOrders() throws Exception {
    }

    @Test
    public void getAllShippedOrders() throws Exception {
    }

    @Test
    public void getAllPaidOrders() throws Exception {
    }

    @Test
    public void getOrdersByMail() throws Exception {
        orderRepository.getOrdersByMail("cenxui1@gmail.com").getOrders().forEach(System.out::println);
    }

    @Test
    public void getAllOrders() throws Exception {
    }

    @Test
    public void getAllProcessingOrders1() throws Exception {
    }

    @Test
    public void getAllShippedOrders1() throws Exception {
    }

    @Test
    public void getAllPaidOrders1() throws Exception {
    }

    @Test
    public void getOrdersByMailAndTime() throws Exception {
    }

    @Test
    public void addOrder() throws Exception {
    }

    @Test
    public void deleteOrder() throws Exception {
    }

    @Test
    public void activeOrder() throws Exception {
    }

}