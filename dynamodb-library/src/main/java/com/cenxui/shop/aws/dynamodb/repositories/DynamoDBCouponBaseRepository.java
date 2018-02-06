package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.cenxui.shop.aws.dynamodb.exceptions.client.coupon.CouponCannotAddException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.coupon.CouponJsonMapException;
import com.cenxui.shop.aws.dynamodb.repositories.util.ItemUtil;
import com.cenxui.shop.repositories.coupon.Coupon;
import com.cenxui.shop.repositories.coupon.CouponBaseRepository;
import com.cenxui.shop.repositories.coupon.CouponOwnerLastKey;
import com.cenxui.shop.repositories.coupon.Coupons;
import com.cenxui.shop.util.JsonUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class DynamoDBCouponBaseRepository implements CouponBaseRepository {
    private final Table couponTable;
    private final String OWNER_INDEX = "ownerIndex";

    DynamoDBCouponBaseRepository(Table couponTable) {
        this.couponTable = couponTable;
    }

    @Override
    public Coupon addCoupon(Coupon coupon) {
        PutItemSpec putItemSpec = new PutItemSpec()
                .withItem(ItemUtil.getCouponItem(coupon))
                .withConditionExpression("attribute_not_exists("+ Coupon.COUPON_TYPE + ")");

        try {
            couponTable.putItem(putItemSpec);
            return coupon;
        }catch (ConditionalCheckFailedException e) {
            throw new CouponCannotAddException(coupon.getMail(), coupon.getCouponType());
        }
    }

    @Override
    public Coupons getCouponsByOwnerMail(String ownerMail) {
        return getCouponsByOwnerMail(ownerMail, null);
    }

    @Override
    public Coupons getCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey couponOwnerLastKey) {

        QuerySpec querySpec = new QuerySpec()
                .withHashKey(Coupon.OWNER_MAIL, ownerMail);

        if (couponOwnerLastKey != null) {
            KeyAttribute k1 = new KeyAttribute(Coupon.MAIL, couponOwnerLastKey.getMail());
            KeyAttribute k2 = new KeyAttribute(Coupon.COUPON_TYPE, couponOwnerLastKey.getCouponType());
            KeyAttribute k3 = new KeyAttribute(Coupon.OWNER_MAIL, couponOwnerLastKey.getOwnerMail());
            KeyAttribute k4 = new KeyAttribute(Coupon.COUPON_STATUS, couponOwnerLastKey.getCouponStatus());

            querySpec.withExclusiveStartKey(k1, k2, k3, k4);
        }

        ItemCollection<QueryOutcome> collection =
                couponTable.getIndex(OWNER_INDEX).query(querySpec);

        List<Coupon> coupons = mapQueryOutcomeToCoupons(collection);

        CouponOwnerLastKey lastKey = getOwnerIndexQueryOutcomeLastKey(collection);

        return Coupons.of(coupons, lastKey);
    }

    private CouponOwnerLastKey getOwnerIndexQueryOutcomeLastKey(ItemCollection<QueryOutcome> collection) {
        QueryOutcome queryOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = queryOutcome.getQueryResult().getLastEvaluatedKey();

        return getCouponOwnerLastKey(lastKeyEvaluated);
    }


    private CouponOwnerLastKey getCouponOwnerLastKey(Map<String, AttributeValue> lastKeyEvaluated) {

        if (lastKeyEvaluated != null) {//null if it is last one
            return CouponOwnerLastKey.of(
                    lastKeyEvaluated.get(Coupon.MAIL).getS(),
                    lastKeyEvaluated.get(Coupon.COUPON_TYPE).getS(),
                    lastKeyEvaluated.get(Coupon.OWNER_MAIL).getS(),
                    lastKeyEvaluated.get(Coupon.COUPON_STATUS).getS());
        }

        return null;
    }


    private List<Coupon> mapQueryOutcomeToCoupons(ItemCollection<QueryOutcome> collection) {
        List<Coupon> coupons = new LinkedList<>();

        collection.forEach(
                (s) -> {
                    coupons.add(getCoupon(s.toJSON()));
                }
        );

        return Collections.unmodifiableList(coupons);
    }

    private Coupon getCoupon(String couponJson) {
        try {
            return JsonUtil.mapToCoupon(couponJson);
        }catch (Exception e) {
            throw new CouponJsonMapException(couponJson);
        }
    }

}
