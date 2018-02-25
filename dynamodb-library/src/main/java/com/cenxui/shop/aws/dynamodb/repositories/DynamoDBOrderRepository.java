package com.cenxui.shop.aws.dynamodb.repositories;

import com.cenxui.shop.aws.dynamodb.exceptions.server.order.*;
import com.cenxui.shop.language.LanguageOrder;
import com.cenxui.shop.repositories.coupon.CouponRepository;
import com.cenxui.shop.repositories.coupon.type.CouponActivity;
import com.cenxui.shop.repositories.coupon.type.CouponType;
import com.cenxui.shop.repositories.coupon.type.exception.CouponActivitiesException;
import com.cenxui.shop.repositories.order.*;
import com.cenxui.shop.repositories.order.attribute.OrderAttributeFilter;
import com.cenxui.shop.repositories.order.attribute.ShippingWay;
import com.cenxui.shop.repositories.product.Product;
import com.cenxui.shop.repositories.product.ProductRepository;

/**
 * order table transaction layer
 *
 */
class DynamoDBOrderRepository implements OrderRepository {

    private final OrderBaseRepository orderRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    DynamoDBOrderRepository(
            DynamoDBOrderBaseRepository orderRepository,
            DynamoDBProductRepository productRepository,
            DynamoDBCouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.couponRepository = couponRepository;
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

        String couponMail = order.getCouponMail();
        String couponType = order.getCouponType();

        checkCouponOrder(order);
        if (couponMail != null && couponType != null) {
            Order couponOrder = useCoupon(trialOrder, couponMail, couponType);
            return orderRepository.addOrder(couponOrder);
        }else {
            return orderRepository.addOrder(trialOrder);
        }
    }

    @Override
    public Order trialOrder(Order order) {

        checkTrialOrder(order);

        //todo possible modify

        int productsPrice = order.getProducts().stream().mapToInt((p)-> {
            String[] s = p.split(";");//todo
            String productName = s[0].trim();
            String version = s[1].trim();
            String count = s[2].trim();

            Product product =
                    productRepository.getProductByProductNameVersion(productName, version);

            if (product == null) {
                throw new OrderProductNotFoundException(productName, version);
            }
            return Integer.valueOf(count) * product.getPrice();
        }).sum();

        return activity(order, productsPrice);
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

    private void checkTrialOrder(Order order) {
        if (order == null) throw new OrderCannotNullException();

        if (!OrderAttributeFilter.checkProducts(order.getProducts()))
            throw new OrderProductsNotAllowedException(order.getProducts());

        if (!OrderAttributeFilter.checkShippingWay(order.getShippingWay()))
            throw new OrderShippedWayNotAllowedException(order.getShippingWay());
    }

    private void checkCouponOrder(Order order) {
        if (order == null) throw new OrderCannotNullException();

        if (!OrderAttributeFilter.checkCoupon(order.getCouponMail(), order.getCouponType())) {
            throw new OrderCouponNotAllowException(order.getCouponMail(), order.getCouponType());
        }
    }

    private Order useCoupon(Order trialOrder, String couponMail, String couponType) {
        try {
            CouponActivity couponActivity = CouponType.getCouponActivity(couponType);
            couponRepository.useCoupon(couponMail, couponType, trialOrder.getMail(), trialOrder.getOrderDateTime());
            return couponActivity.getCouponOrder(trialOrder);
        }catch (CouponActivitiesException e) {
            throw new OrderCouponActivityNotExistException(couponType);
        }
    }

    private Order activity(Order order, int productsPrice) {
        String activity = null;
        int shippingCost, price;

        if (ShippingWay.SHOP.equals(order.getShippingWay())) {
            shippingCost = 60;
            if (productsPrice >= 1000) {
                price = productsPrice; //no shipping cost if productsPrice more than 1000
                activity = LanguageOrder.EVENT_1;
            }else {
                price = shippingCost + productsPrice;
            }
        }else if (ShippingWay.HOME.equals(order.getShippingWay())) {
            shippingCost = 100;
            if (productsPrice >= 1500) {
                price = productsPrice; //no shipping cost if productsPrice more than 1000
                activity = LanguageOrder.EVENT_2;
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
                order.getCouponMail(),
                order.getCouponType(),
                order.getCouponActivity(),
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
}
