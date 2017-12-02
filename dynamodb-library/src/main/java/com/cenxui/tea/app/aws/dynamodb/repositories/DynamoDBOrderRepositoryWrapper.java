package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.aws.dynamodb.exceptions.order.OrderProductFormatException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.product.ProductCurrencyNotConsistException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.product.ProductNotFoundException;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.repositories.order.Orders;
import com.cenxui.tea.app.repositories.order.report.CashReport;
import com.cenxui.tea.app.repositories.product.Price;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.util.JsonUtil;

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
    public Orders getAllShippedOrders(String shippedDate, String shippedTime, Integer limit) {
        return orderRepository.getAllShippedOrders(shippedDate,shippedTime, limit);
    }

    @Override
    public Orders getAllPaidOrders() {
        return orderRepository.getAllPaidOrders();
    }

    @Override
    public Orders getAllPaidOrders(String paidDate, String paidTime, Integer limit) {
        return orderRepository.getAllPaidOrders(paidDate, paidTime, limit);
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


        Float orderPrice = 0F;

        List<String> products = order.getProducts();

        String currency = null;

        for (String product: products) {
            String[] s = product.split(";");//todo

            if (s.length != 3) throw new OrderProductFormatException(product);

            String productName = s[0].trim();
            String version = s[1].trim();
            String count = s[2].trim();

            Price price = productRepository.getProductPrice(productName, version);

            if (price == null) throw new ProductNotFoundException(productName, version);

            if (currency != null && !currency.equals(price.getCurrency())) {
                throw new ProductCurrencyNotConsistException(JsonUtil.mapToJson(products));
            }else {
                currency = price.getCurrency();
            }

            orderPrice = orderPrice + price.getMoney() * Float.valueOf(count);
        }

        return orderRepository.addOrder(Order.of(
                order.getMail(),
                order.getProducts(),
                order.getPurchaser(),
                currency,
                orderPrice,
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
    public CashReport getCashAllReport() {
        return orderRepository.getCashAllReport();
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
