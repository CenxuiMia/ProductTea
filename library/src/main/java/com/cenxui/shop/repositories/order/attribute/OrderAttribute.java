package com.cenxui.shop.repositories.order.attribute;

import java.util.List;

public class OrderAttribute {

    public static boolean checkProducts(List products) {
        if (products == null || products.size() == 0) return false;
        //todo
        return true;
    }

    public static boolean checkPurchaser(String purchaser) {
        if (isEmpty(purchaser)) return false;
        //todo
        return true;
    }

    public static boolean checkPurchaserPhone(String purchaserPhone) {
        return isDigit(purchaserPhone);
    }

    public static boolean checkPaymentMethod(String paymentMethod) {
        return  PaymentMethod.ACCOUNT.equals(paymentMethod) ||
                PaymentMethod.VIRTUAL_ACCOUNT.equals(paymentMethod) ||
                PaymentMethod.CREDIT_CARD.equals(paymentMethod);
    }

    public static boolean checkBankInformation(String paymentMethod, String bankInformation) {
        if (isEmpty(paymentMethod)) return false;

        switch (paymentMethod) {
            case PaymentMethod.ACCOUNT:
                if (isDigit(bankInformation) &&
                        bankInformation.length() == 8) {
                    return true;
                }
                return false;
            case PaymentMethod.VIRTUAL_ACCOUNT:
                //todo
                return true;
            case PaymentMethod.CREDIT_CARD:
                if (bankInformation == null) {
                    return true;
                }
                return false;
        }
        return false;
    }

    public static boolean checkReceiver(String receiver) {
        if (isEmpty(receiver)) return false;
        //todo
        return true;
    }

    public static boolean checkReceiverPhone(String receiverPhone) {
        return isDigit(receiverPhone);
    }

    public static boolean checkShippingWay(String shippingWay) {
        return ShippingWay.SHOP.equals(shippingWay) || ShippingWay.HOME.equals(shippingWay);
    }

    public static boolean checkShippingAddress(String shippingAddress) {
        if (isEmpty(shippingAddress)) return false;
        //todo
        return true;
    }

    public static boolean checkComment(String comment) {
        if (!isEmpty(comment)) {
            //todo
        }

        return true;
    }

    private static boolean isDigit(String number) {
        if (isEmpty(number)) return false;

        try {
            Double.parseDouble(number);
        } catch(NumberFormatException e) {
            return false;
        }

        return true;
    }


    private static boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) return true;
        return false;
    }
}
