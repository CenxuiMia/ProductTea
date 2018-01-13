package com.cenxui.shop.aws.dynamodb.repositories;

import com.cenxui.shop.aws.dynamodb.exceptions.server.order.OrderShippedWayCannotNullException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.order.OrderCannotNullException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.order.OrderProductsCannotNullException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.order.OrderShippedWayException;
import com.cenxui.shop.repositories.order.*;
import com.cenxui.shop.repositories.product.ProductRepository;

class DynamoDBOrderRepository implements OrderRepository {

    private final OrderBaseRepository orderRepository;
    private final ProductRepository productRepository;

    DynamoDBOrderRepository(
            DynamoDBOrderBaseRepository orderRepository,
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
    public Orders getOrdersByMailAndTime(String mail, String time) {
        return orderRepository.getOrdersByMailAndTime(mail, time);
    }

    @Override
    public Orders getOrdersByPaidDate(String paidDate) {
        return orderRepository.getOrdersByPaidDate(paidDate);
    }

    @Override
    public Orders getOrdersByPaidDateAndPaidTime(String paidDate, String paidTime) {
        return orderRepository.getOrdersByPaidDateAndPaidTime(paidDate, paidTime);
    }

    @Override
    public Orders getOrdersByProcessingDate(String processingDate) {
        return orderRepository.getOrdersByProcessingDate(processingDate);
    }

    @Override
    public Orders getOrdersByProcessingDateAndOwner(String processingDate, String owner) {
        return orderRepository.getOrdersByProcessingDateAndOwner(processingDate, owner);
    }

    @Override
    public Orders getOrdersByShippedDate(String shippedDate) {
        return orderRepository.getOrdersByShippedDate(shippedDate);
    }

    @Override
    public Orders getOrdersByShippedDateAndTime(String shippedDate, String shippedTime) {
        return orderRepository.getOrdersByShippedDateAndTime(shippedDate, shippedTime);
    }

    @Override
    public Order addOrder(Order order) {

        Order trialOrder = trialOrder(order);

        return orderRepository.addOrder(Order.of(
                order.getMail(),
                order.getOrderDateTime(),
                order.getProducts(),
                order.getPurchaser(),
                trialOrder.getShippingCost(),
                trialOrder.getProductsPrice(),
                trialOrder.getActivity(),
                trialOrder.getPrice(),
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
    public Order trialOrder(Order order) {

        if (order == null) throw new OrderCannotNullException();

        if (order.getProducts() == null) throw new OrderProductsCannotNullException(order);

        if (order.getShippingWay() == null) throw new OrderShippedWayCannotNullException(order);

        int price;

        int shippingCost;

        int productsPrice;

        //todo possible modify

        if (ShippedWay.SHOP.equals(order.getShippingWay())) {
            shippingCost = 60;
        }else if (ShippedWay.HOME.equals(order.getShippingWay())) {
            shippingCost = 100;
        }else {
            throw new OrderShippedWayException(order.getShippingWay());
        }

        productsPrice = productRepository.getProductsPrice(order.getProducts()).getValue();

        price = shippingCost + productsPrice;

        return Order.of(
                null,
                null,
                order.getProducts(),
                null,
                shippingCost,
                productsPrice,
                null,
                price,
                order.getPaymentMethod(),
                null,
                null,
                order.getShippingWay(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null

        );
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
