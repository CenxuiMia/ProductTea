package com.cenxui.shop.repositories.order;

public class PaymentMethod {
    public static final String ACCOUNT = "account";
    public static final String VIRTUAL_ACCOUNT = "virtualAccount";
    public static final String CREDIT_CARD = "creditCard";

    public static boolean allowed(String paymentMethod) {
        return ACCOUNT.equals(paymentMethod) ||
                VIRTUAL_ACCOUNT.equals(paymentMethod) ||
                CREDIT_CARD.equals(paymentMethod);
    }
}
