package com.cenxui.shop.repositories.coupon.type;

import com.cenxui.shop.language.LanguageCoupon;
import com.cenxui.shop.repositories.order.Order;

class DisCount100CouponActivity implements CouponActivity {

    @Override
    public Order getCouponOrder(Order order) {
        return  Order.of(
                order.getMail(),
                order.getOrderDateTime(),
                order.getProducts(),
                order.getPurchaser(),
                order.getPurchaserPhone(),
                order.getShippingCost(),
                order.getProductsPrice(),
                order.getActivity(),
                order.getPrice() - 100,
                order.getPaymentMethod(),
                order.getBankInformation(),
                order.getCouponMail(),
                order.getCouponType(),
                getCouponActivityMessage(),
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

    @Override
    public String getCouponActivityMessage() {
        return LanguageCoupon.DISCOUNT100_ACTIVITY;
    }
}
