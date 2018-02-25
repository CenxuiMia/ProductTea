package com.cenxui.shop.repositories.product.attribute;

import java.util.List;

//todo
public class ProductAttributeFilter {
    boolean checkAmount(Integer amount) {
        return amount != null && amount >=0;
    }

    boolean checkIntroduction(String introduction) {
        return true;
    }

    boolean checkSpec(String spec) {
        return true;
    }

    boolean checkDetails(String details) {
        return true;
    }

    boolean checkSmallImage(String smallImage) {
        return true;
    }

    boolean checkVideo(String video) {
        return true;
    }

    boolean checkSmallImages(List<String> smallImages) {
        return true;
    }

    boolean checkPrice(Integer price) {
        return true;
    }

    boolean checkOriginalPrice(Integer originalPrice) {
        return true;
    }

    boolean checkSubtractAmount(Integer amount) {
        return amount != null && amount >0 && amount < 11;
    }

    boolean checkEnableEvent(Boolean enableEvent) {
        return true;
    }

    boolean checkEnableCoupon(Boolean enableCoupon) {
        return true;
    }

    boolean checkTag(String tag) {
        return true;
    }

}
