package com.cenxui.shop.repositories.order.attribute;

import com.cenxui.shop.repositories.coupon.type.CouponType;

import java.util.List;

public class OrderAttributeFilter {
    private static final int productsLimit = 100;
    private static final int productNameLimit = 20;
    private static final int productVersionLimit = 20;
    private static final int productCountLimit = 11;

    private static final int purchaserLimit = 30;
    private static final int purchaserPhoneLimit = 20;
    private static final int receiverLimit = 30;
    private static final int receiverPhoneLimit = 20;
    private static final int commentLimit = 200;

    public static boolean checkProducts(List<String> products) {
        if (products == null || products.size() == 0 || products.size() > productsLimit) return false;

        //todo add set check

        for (String product: products) {
            try {
                String[] s = product.split(";");//todo

                if (s.length != 3) {
                    return false;
                }

                String productName = s[0].trim();

                if (productName.length() == 0 || product.length() > productNameLimit) {
                    return false;
                }

                String version = s[1].trim();

                if (version.length() == 0 || version.length() > productVersionLimit) {
                    return false;
                }

                Integer count = Integer.valueOf(s[2].trim());

                if (count < 1 ||count > productCountLimit) {
                    return false;
                }

            }catch (Exception e) {
                return false;
            }
        }

        //todo
        return true;
    }

    public static boolean checkPurchaser(String purchaser) {
        if (isEmpty(purchaser)) return false;

        if (purchaser.length() > purchaserLimit) return false;

        return true;
    }

    public static boolean checkPurchaserPhone(String purchaserPhone) {
        if (isEmpty(purchaserPhone)) return false;

        if (purchaserPhone.length() > purchaserPhoneLimit) {
            return false;
        }

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

    public static boolean checkCoupon(String couponMail, String couponType) {
        if (couponMail == null || couponType == null) {
            return true;
        }else if (couponMail != null && couponType != null) {
            return CouponType.contain(couponType);
        }else {
            return false;
        }
    }

    public static boolean checkReceiver(String receiver) {
        if (isEmpty(receiver)) return false;

        if (receiver.length() > receiverLimit) {
            return false;
        }

        //todo
        return true;
    }

    public static boolean checkReceiverPhone(String receiverPhone) {
        if (isEmpty(receiverPhone)) return false;

        if (receiverPhone.length() > receiverPhoneLimit) return false;

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
            if (comment.length() > commentLimit) {
                return false;
            }
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
