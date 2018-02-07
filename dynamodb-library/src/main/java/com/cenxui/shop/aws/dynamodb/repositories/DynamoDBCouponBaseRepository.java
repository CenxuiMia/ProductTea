package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.cenxui.shop.aws.dynamodb.exceptions.client.coupon.CouponCannotAddException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.coupon.CouponJsonMapException;
import com.cenxui.shop.aws.dynamodb.repositories.util.ItemUtil;
import com.cenxui.shop.repositories.coupon.*;
import com.cenxui.shop.repositories.coupon.type.CouponType;
import com.cenxui.shop.util.JsonUtil;

import java.io.IOException;
import java.util.*;

class DynamoDBCouponBaseRepository implements CouponBaseRepository {
    private final Table couponTable;
    private static final String OWNER_INDEX = "ownerIndex";

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
    public Coupon addSignUpCoupon(String mail) {
        return addCoupon(CouponType.getSignUpCoupon(mail));
    }

    @Override
    public Coupon addInvitationCoupon(String mail, String invitationMail) {
        return addCoupon(CouponType.getInvitationCoupon(mail, invitationMail));
    }

    @Override
    public Coupon useCoupon(String couponMail, String couponType, String mail) {
        //check the mail equal the owner mail
        //check the couponStatus is active
        //check the expirationTime
        //update the couponStatus to used

        UpdateItemSpec spec = new UpdateItemSpec()
                .withPrimaryKey(Coupon.MAIL, mail, Coupon.OWNER_MAIL, mail)
                .withConditionExpression(
                        Coupon.OWNER_MAIL + "=:ow AND " +
                                Coupon.COUPON_STATUS + " =:csa AND " + Coupon.EXPIRATION_TIME + "> :ept" )
                .withUpdateExpression("set " + Coupon.COUPON_STATUS + ":csu" )
                .withValueMap(
                        new ValueMap()
                                .withString(":ow", mail)
                                .withString(":csa", CouponStatus.ACTIVE)
                                .withString(":csu", CouponStatus.USED)
                                .withLong(":ept", System.currentTimeMillis()));
        String couponJson = couponTable.updateItem(spec).getItem().toJSON();

        return getCoupon(couponJson);
    }

    @Override
    public Coupons getCouponsByOwnerMail(String ownerMail) {
        return getCouponsByOwnerMail(ownerMail, null);
    }

    /**
     * get coupon by hashkey ownerMail rangekey active filt with expired time.
     * @param ownerMail
     * @param couponOwnerLastKey
     * @return
     */
    @Override
    public Coupons getCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey couponOwnerLastKey) {
        String currentTime = String.valueOf(System.currentTimeMillis());

        Map<String, AttributeValue> expressionAttributeValues =
                new TreeMap<>();
        expressionAttributeValues.put(":val", new AttributeValue().withN(currentTime));

        QuerySpec querySpec = new QuerySpec()
                .withHashKey(Coupon.OWNER_MAIL, ownerMail)
                .withRangeKeyCondition(new RangeKeyCondition(Coupon.COUPON_STATUS).eq(CouponStatus.ACTIVE))
                .withFilterExpression(Coupon.EXPIRATION_TIME + "> :val" );

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
