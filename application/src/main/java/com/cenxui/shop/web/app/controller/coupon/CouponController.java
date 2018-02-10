package com.cenxui.shop.web.app.controller.coupon;

import com.cenxui.shop.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.shop.repositories.coupon.CouponRepository;
import com.cenxui.shop.repositories.coupon.attribute.CouponAttributeFilter;
import com.cenxui.shop.repositories.coupon.type.CouponType;
import com.cenxui.shop.util.JsonUtil;
import com.cenxui.shop.web.app.config.AWSDynamoDBConfig;
import com.cenxui.shop.web.app.controller.CoreController;
import com.cenxui.shop.web.app.controller.util.Header;
import com.cenxui.shop.web.app.controller.util.Param;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class CouponController extends CoreController {

    public static final CouponRepository couponRepository =
            DynamoDBRepositoryService.getCouponRepository(
                    AWSDynamoDBConfig.REGION,
                    AWSDynamoDBConfig.COUPON_TABLE);

    public static final Route getActiveCoupons = (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);
        return JsonUtil.mapToJsonIgnoreNull(couponRepository.getActiveCouponsByOwnerMail(mail));
    };

    public static final Route getAvailableCoupons = (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);
        return JsonUtil.mapToJsonIgnoreNull(couponRepository.getAvailableCouponsByOwnerMail(mail));
    };

    public static final Route addCoupon = (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);
        String couponType = getCouponType(request.params());

        checkCouponType(couponType);
        //todo
        return JsonUtil.mapToJsonIgnoreNull(couponRepository.addSignUpCoupon(mail));
    };

    private static void checkCouponType(String couponType) {
        if (!CouponAttributeFilter.checkCouponType(couponType)) {
            throw new CouponControllerClientException("request parm couponType not allow");
        }
    }

    private static String getCouponType(Map<String, String> map) {
        return map.get(Param.COUPON_TYPE);
    }

}
