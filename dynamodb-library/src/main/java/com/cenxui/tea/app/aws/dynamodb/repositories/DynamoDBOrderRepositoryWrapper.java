package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.aws.dynamodb.exceptions.map.order.OrderProductFormatException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.product.ProductNotFoundException;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.repositories.order.Orders;
import com.cenxui.tea.app.repositories.product.ProductRepository;

import java.util.List;

class DynamoDBOrderRepositoryWrapper implements OrderRepository {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    DynamoDBOrderRepositoryWrapper(
            DynamoDBOrderRepository orderRepository,
            DynamoDBProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Orders getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public Orders getAllOrders(String mail, String time, Integer limit) {
        return orderRepository.getAllOrders(mail, time, limit);
    }

    @Override
    public Orders getAllProcessingOrders() {
        return orderRepository.getAllProcessingOrders();
    }

    @Override
    public Orders getAllProcessingOrders(String processingDate, Integer limit) {
        return orderRepository.getAllProcessingOrders(processingDate, limit);
    }

    @Override
    public Orders getAllShippedOrders() {
        return orderRepository.getAllShippedOrders();
    }

    @Override
    public Orders getAllShippedOrders(String shippedTime, Integer limit) {
        return orderRepository.getAllShippedOrders(shippedTime, limit);
    }

    @Override
    public Orders getAllPaidOrders() {
        return orderRepository.getAllPaidOrders();
    }

    @Override
    public Orders getAllPaidOrders(String paidTime, Integer limit) {
        return orderRepository.getAllPaidOrders(paidTime, limit);
    }

    @Override
    public Orders getOrdersByMail(String mail) {
        return orderRepository.getOrdersByMail(mail);
    }

    @Override
    public Order getOrdersByMailAndTime(String mail, String time) {
        return orderRepository.getOrdersByMailAndTime(mail, time);
    }

    @Override
    public Order addOrder(Order order) {

        Float money = 0F;

        List<String> products = order.getProducts();

        for (String product: products) {
            String[] s = product.split(";");

            if (s.length != 3) throw new OrderProductFormatException(product);

            String productName = s[0].trim();
            String version = s[1].trim();

            Float price = productRepository.getProductPrice(productName, version);

            if (price == null) throw new ProductNotFoundException(productName, version);

            money = money + price * Float.valueOf(s[2].trim());
        }

        return orderRepository.addOrder(Order.of(
                order.getMail(),
                order.getProducts(),
                order.getPurchaser(),
                money,
                order.getReceiver(),
                order.getPhone(),
                order.getAddress(),
                order.getComment(),
                order.getPaidTime(),
                order.getProcessingDate(),
                order.getShippedTime(),
                order.getIsActive()));
    }

    @Override
    public boolean deleteOrder(String mail, String time) {
        orderRepository.deleteOrder(mail, time);
        return false;
    }

    @Override
    public Order activeOrder(String mail, String time) {
        return orderRepository.activeOrder(mail, time);
    }

    @Override
    public Order deActiveOrder(String mail, String time) {

        return orderRepository.deActiveOrder(mail, time);
    }

    @Override
    public Order payOrder(String mail, String time) {
        return orderRepository.payOrder(mail, time);
    }

    @Override
    public Order payOrder(String mail, String time, String paidTime) {
        return orderRepository.payOrder(mail, time, paidTime);
    }

    @Override
    public Order dePayOrder(String mail, String time) {
        return orderRepository.dePayOrder(mail, time);
    }

    @Override
    public Order shipOrder(String mail, String time) {
        return orderRepository.shipOrder(mail, time);
    }

    @Override
    public Order shipOrder(String mail, String time, String shippedTime) {
        return orderRepository.shipOrder(mail, time, shippedTime);
    }

    @Override
    public Order deShipOrder(String mail, String time) {
        return orderRepository.deShipOrder(mail, time);
    }
}
