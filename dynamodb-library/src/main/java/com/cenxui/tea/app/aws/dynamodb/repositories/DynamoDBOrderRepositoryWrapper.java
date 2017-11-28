package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.aws.dynamodb.exceptions.map.order.OrderProductFormatException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.product.ProductNotFoundException;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.repositories.order.OrderResult;
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
    public OrderResult getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public OrderResult getAllOrdersByLastKey(Integer limit, String mail, String time) {
        return orderRepository.getAllOrdersByLastKey(limit, mail, time);
    }

    @Override
    public OrderResult getAllProcessingOrders() {
        return orderRepository.getAllProcessingOrders();
    }

    @Override
    public OrderResult getAllProcessingOrders(Integer limit, String mail, String time) {
        return orderRepository.getAllProcessingOrders(limit, mail, time);
    }

    @Override
    public OrderResult getAllShippedOrders() {
        return orderRepository.getAllShippedOrders();
    }

    @Override
    public OrderResult getAllShippedOrders(Integer limit, String mail, String time) {
        return orderRepository.getAllShippedOrders(limit, mail, time);
    }

    @Override
    public OrderResult getAllPaidOrders() {
        return orderRepository.getAllPaidOrders();
    }

    @Override
    public OrderResult getAllPaidOrders(Integer limit, String mail, String time) {
        return orderRepository.getAllPaidOrders(limit, mail, time);
    }

    @Override
    public OrderResult getOrdersByMail(String mail) {
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
                order.getPaidDate(),
                order.getProcessingDate(),
                order.getShippedDate(),
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
    public Order dePayOrder(String mail, String time) {
        return orderRepository.dePayOrder(mail, time);
    }

    @Override
    public Order shipOrder(String mail, String time) {
        return orderRepository.shipOrder(mail, time);
    }

    @Override
    public Order deShipOrder(String mail, String time) {
        return orderRepository.deShipOrder(mail, time);
    }
}
