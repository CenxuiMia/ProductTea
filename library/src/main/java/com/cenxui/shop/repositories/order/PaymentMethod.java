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

    public static boolean allowedBankInformation(String paymentMethod, String bankInformation) {
        if (paymentMethod == null) return false;

        switch (paymentMethod) {
            case ACCOUNT:
                if (bankInformation != null && bankInformation.length() == 8) {
                    return true;
                }
                return false;
            case VIRTUAL_ACCOUNT:
                //todo
                return true;
            case CREDIT_CARD:
                if (bankInformation == null) {
                    return true;
                }
                return false;
        }
        return false;

    }
}
