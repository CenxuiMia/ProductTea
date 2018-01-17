package com.cenxui.shop.repositories.order;

public class ShippingWay {
    public static final String SHOP = "shop";
    public static final String HOME = "home";

    public static boolean allowed(String shippingWay) {
        return SHOP.equals(shippingWay) || HOME.equals(shippingWay);
    }
}
