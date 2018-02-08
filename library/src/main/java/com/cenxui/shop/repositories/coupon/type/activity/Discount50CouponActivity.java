package com.cenxui.shop.repositories.coupon.type.activity;

import com.cenxui.shop.repositories.order.Order;

class Discount50CouponActivity implements CouponActivity {
    @Override
    public Order getCouponOrder(Order order) {
        return order;
    }
}
