package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.aws.dynamodb.exceptions.server.order.OrderCannotNullException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.server.order.OrderProductsCannotNullException;
import com.cenxui.tea.app.repositories.order.*;
import com.cenxui.tea.app.repositories.order.CashReport;
import com.cenxui.tea.app.repositories.product.Price;
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
    public Orders getAllActiveOrders() {
        return orderRepository.getAllActiveOrders();
    }

    @Override
    public Orders getAllActiveOrders(String mail, String time, Integer limit) {
        return orderRepository.getAllActiveOrders(mail, time, limit);
    }

    @Override
    public Orders getAllPaidOrders() {
        return orderRepository.getAllPaidOrders();
    }

    @Override
    public Orders getAllPaidOrders(OrderPaidLastKey orderPaidLastKey, Integer limit) {
        return orderRepository.getAllPaidOrders(orderPaidLastKey, limit);
    }

    @Override
    public Orders getAllProcessingOrders() {
        return orderRepository.getAllProcessingOrders();
    }

    @Override
    public Orders getAllProcessingOrders(OrderProcessingLastKey processingLastKey, Integer limit) {
        return orderRepository.getAllProcessingOrders(processingLastKey, limit);
    }

    @Override
    public Orders getAllShippedOrders() {
        return orderRepository.getAllShippedOrders();
    }

    @Override
    public Orders getAllShippedOrders(OrderShippedLastKey orderShippedLastKey, Integer limit) {
        return orderRepository.getAllShippedOrders(orderShippedLastKey, limit);
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
        if (order == null) throw new OrderCannotNullException();

        if (order.getProducts() == null) throw new OrderProductsCannotNullException(order);

        List<String> products = order.getProducts();

        Price price = productRepository.getProductsPrice(products);

        return orderRepository.addOrder(Order.of(
                order.getMail(),
                order.getOrderDateTime(),
                order.getProducts(),
                order.getPurchaser(),
                price.getValue(),
                order.getPaymentMethod(),
                order.getReceiver(),
                order.getPhone(),
                order.getShippingWay(),
                order.getShippingAddress(),
                order.getComment(),
                order.getPaidDate(),
                order.getPaidTime(),
                order.getProcessingDate(),
                order.getShippedDate(),
                order.getShippedTime(),
                order.getIsActive(),
                order.getOwner()));
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
    public Order payOrder(String mail, String time, String payDate,String paidTime) {
        return orderRepository.payOrder(mail, time, payDate, paidTime);
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
    public Order shipOrder(String mail, String time, String shippedDate, String shippedTime) {
        return orderRepository.shipOrder(mail, time, shippedDate, shippedTime);
    }

    @Override
    public Order deShipOrder(String mail, String time) {
        return orderRepository.deShipOrder(mail, time);
    }

    @Override
    public CashReport getAllCashReport() {
        return orderRepository.getAllCashReport();
    }

    @Override
    public CashReport getDailyCashReport(String paidDate) {
        return orderRepository.getDailyCashReport(paidDate);
    }

    @Override
    public CashReport getRangeCashReport(String fromPaidDate, String toPaidDate) {
        return orderRepository.getRangeCashReport(fromPaidDate, toPaidDate);
    }
}
