package com.cenxui.shop.aws.dynamodb.repositories;

import com.cenxui.shop.aws.dynamodb.exceptions.server.order.*;
import com.cenxui.shop.repositories.order.*;
import com.cenxui.shop.repositories.order.attribute.OrderAttributeFilter;
import com.cenxui.shop.repositories.order.attribute.ShippingWay;
import com.cenxui.shop.repositories.product.ProductRepository;

/**
 * order table transaction layer
 *
 */
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
    public Orders getAllBankOrders() {
        return orderRepository.getAllBankOrders();
    }

    @Override
    public Orders getAllBankOrders(OrderBankLastKey orderBankLastKey, Integer limit) {
        return orderRepository.getAllBankOrders(orderBankLastKey, limit);
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
    public Orders getOrdersByBankInformation(String bankInformation) {
        return orderRepository.getOrdersByBankInformation(bankInformation);
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

        return orderRepository.addOrder(trialOrder);
    }


    @Override
    public Order trialOrder(Order order) {

        checkTrialOrder(order);

        int price, shippingCost;

        //todo possible modify

        int productsPrice = 0;

        for (String product: order.getProducts()) {
            String[] s = product.split(";");//todo

            String productName = s[0].trim();
            String version = s[1].trim();
            String count = s[2].trim();

            Integer productPrice = productRepository.getProductPrice(productName, version);

            if (productPrice == null) {
                throw new OrderProductNotFoundException(productName, version);
            }

            productsPrice = productsPrice + productPrice * Integer.valueOf(count);
        }

        //todo modify business

        String activity = null;

        if (ShippingWay.SHOP.equals(order.getShippingWay())) {
            shippingCost = 60;
            if (productsPrice >= 1000) {
                price = productsPrice; //no shipping cost if productsPrice more than 1000
                activity = "滿一千免運費";
            }else {
                price = shippingCost + productsPrice;
            }
        }else if (ShippingWay.HOME.equals(order.getShippingWay())) {
            shippingCost = 100;
            if (productsPrice >= 1500) {
                price = productsPrice; //no shipping cost if productsPrice more than 1000
                activity = "滿一千五免運費";
            }else {
                price = shippingCost + productsPrice;
            }

        }else {
            throw new OrderShippedWayNotAllowedException(order.getShippingWay());
        }

        return Order.of(
                order.getMail(),
                order.getOrderDateTime(),
                order.getProducts(),
                order.getPurchaser(),
                order.getPurchaserPhone(),
                shippingCost,
                productsPrice,
                activity,
                price,
                order.getPaymentMethod(),
                order.getBankInformation(),
                order.getReceiver(),
                order.getReceiverPhone(),
                order.getShippingWay(),
                order.getShippingAddress(),
                order.getComment(),
                order.getPaidDate(),
                order.getPaidTime(),
                order.getProcessingDate(),
                order.getShippedDate(),
                order.getShippedTime(),
                order.getIsActive(),
                order.getOwner()
        );
    }

    private void checkTrialOrder(Order order) {
        if (order == null) throw new OrderCannotNullException();

        if (!OrderAttributeFilter.checkProducts(order.getProducts()))
            throw new OrderProductsNotAllowedException(order.getProducts());

        if (!OrderAttributeFilter.checkShippingWay(order.getShippingWay()))
            throw new OrderShippedWayNotAllowedException(order.getShippingWay());
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
