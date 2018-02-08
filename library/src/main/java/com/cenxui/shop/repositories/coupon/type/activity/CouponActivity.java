package com.cenxui.shop.repositories.coupon.type.activity;

import com.cenxui.shop.repositories.order.Order;

public interface CouponActivity {
    Order getCouponOrder(Order order);
}
