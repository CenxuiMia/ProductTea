package com.cenxui.shop.repositories.order;

import com.cenxui.shop.repositories.order.attribute.PaymentMethod;
import org.junit.Test;

public class PaymentMethodTest {
    @Test
    public void allowedBankInformation() throws Exception {

        System.out.println(PaymentMethod.allowedBankInformation(PaymentMethod.ACCOUNT, null));

        System.out.println(PaymentMethod.allowedBankInformation(PaymentMethod.ACCOUNT, "00812345"));

        System.out.println(PaymentMethod.allowedBankInformation(null, "00812345"));

    }

}