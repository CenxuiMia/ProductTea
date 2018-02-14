package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.cenxui.shop.aws.dynamodb.exceptions.client.coupon.CouponCannotAddException;
import com.cenxui.shop.aws.dynamodb.exceptions.client.coupon.CouponCannotUsedException;
import com.cenxui.shop.aws.dynamodb.exceptions.client.coupon.CouponInvitationCannotAddException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.coupon.CouponCannotNullException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.coupon.CouponJsonMapException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.coupon.CouponPrimaryKeyCannotNullException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.coupon.CouponTypeNotAllowedException;
import com.cenxui.shop.aws.dynamodb.repositories.util.ItemUtil;
import com.cenxui.shop.repositories.coupon.*;
import com.cenxui.shop.repositories.coupon.type.CouponAvailable;
import com.cenxui.shop.repositories.coupon.type.CouponType;
import com.cenxui.shop.repositories.coupon.type.exception.CouponActivitiesException;
import com.cenxui.shop.util.JsonUtil;

import java.util.*;
import java.util.stream.Collectors;

class DynamoDBCouponBaseRepository implements CouponBaseRepository {
    private final Table couponTable;
    private static final String OWNER_INDEX = "ownerIndex";

    DynamoDBCouponBaseRepository(Table couponTable) {
        this.couponTable = couponTable;
    }

    @Override
    public Coupon addCoupon(Coupon coupon) {
        checkCoupon(coupon);

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
        checkPrimaryKey(mail);
        return addCoupon(CouponType.getSignUpCoupon(mail));
    }

    @Override
    public Coupon addInvitationCoupon(String mail, String invitationMail) {
        checkPrimaryKey(mail);
        checkPrimaryKey(invitationMail);
        checkInvitationCoupon(mail, invitationMail);

        return addCoupon(CouponType.getInvitationCoupon(mail, invitationMail));
    }

    private void checkInvitationCoupon(String mail, String invitationMail) {
        if (invitationMail.equals(mail)) throw new CouponInvitationCannotAddException(invitationMail);
    }

    @Override
    public Coupon useCoupon(String couponMail, String couponType, String mail, Long orderDateTime) {
        checkPrimaryKey(couponMail);
        checkPrimaryKey(couponType);
        checkPrimaryKey(mail);
        checkPrimaryKey(orderDateTime);

        //check the mail equal the owner mail
        //check the couponStatus is active
        //check the expirationTime
        //update the couponStatus to used

        UpdateItemSpec spec = new UpdateItemSpec()
                .withPrimaryKey(Coupon.MAIL, couponMail, Coupon.COUPON_TYPE, couponType)
                .withConditionExpression(
                                Coupon.OWNER_MAIL + " =:ow AND " +
                                Coupon.COUPON_STATUS + " =:csa AND " +
                                Coupon.EXPIRATION_TIME + " >:ept")
                .withUpdateExpression("set " +
                        Coupon.COUPON_STATUS + "=:csu , " +
                        Coupon.ORDER_DATETIME + "=:odt")
                .withValueMap(
                        new ValueMap()
                                .withString(":ow", mail)
                                .withString(":csa", CouponStatus.ACTIVE)
                                .withString(":csu", CouponStatus.USED)
                                .withLong(":ept", System.currentTimeMillis())
                                .withLong(":odt", orderDateTime))
                .withReturnValues(ReturnValue.ALL_NEW);
        try {
            UpdateItemOutcome outcome = couponTable.updateItem(spec);
            String couponJson = outcome.getItem().toJSON();
            return getCoupon(couponJson);
        }catch (ConditionalCheckFailedException e) {
            throw new CouponCannotUsedException(couponMail, couponType);
        }
    }

    @Override
    public Coupons getCouponsByOwnerMail(String ownerMail) {
        return getCouponByOwnerMail(ownerMail, null);
    }

    @Override
    public Coupons getCouponByOwnerMail(String ownerMail, CouponOwnerLastKey couponOwnerLastKey) {
        checkPrimaryKey(ownerMail);
        QuerySpec querySpec = new QuerySpec()
                .withHashKey(Coupon.OWNER_MAIL, ownerMail);

        if (couponOwnerLastKey != null) {

            KeyAttribute[] keys = getCouponOwnerLastKeyKeyAttributes(couponOwnerLastKey);

            querySpec.withExclusiveStartKey(keys);
        }

        return getCoupons(querySpec);
    }

    @Override
    public Coupons getActiveCouponsByOwnerMail(String ownerMail) {
        return getActiveCouponsByOwnerMail(ownerMail, null);
    }

    /**
     * get coupon by hashkey ownerMail rangekey active filt with expired time.
     * @param ownerMail
     * @param couponOwnerLastKey
     * @return
     */
    @Override
    public Coupons getActiveCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey couponOwnerLastKey) {
        checkPrimaryKey(ownerMail);

        QuerySpec querySpec = new QuerySpec()
                .withHashKey(Coupon.OWNER_MAIL, ownerMail)
                .withRangeKeyCondition(new RangeKeyCondition(Coupon.COUPON_STATUS).eq(CouponStatus.ACTIVE))
                .withFilterExpression(Coupon.EXPIRATION_TIME + "> :val" )
                .withValueMap(new ValueMap().withLong(":val", System.currentTimeMillis()));

        if (couponOwnerLastKey != null) {

            KeyAttribute[] keys = getCouponOwnerLastKeyKeyAttributes(couponOwnerLastKey);

            querySpec.withExclusiveStartKey(keys);
        }

        return getCoupons(querySpec);
    }

    @Override
    public CouponAvailable getAvailableCouponsByOwnerMail(String ownerMail) {
        return getAvailableCouponsByOwnerMail(ownerMail, null);
    }

    @Override
    public CouponAvailable getAvailableCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey couponOwnerLastKey) {

        Coupons coupons = getCouponByOwnerMail(ownerMail, couponOwnerLastKey);

        Set<String> couponSet = coupons.getCoupons()
                .stream()
                .map(Coupon::getCouponType)
                .collect(Collectors.toSet());

        List<String> couponAvailable = CouponType.getCouponTypeSet()
                .stream()
                .filter(s-> !couponSet.contains(s))
                .collect(Collectors.toList());

        return CouponAvailable.of(couponAvailable);
    }



    private Coupons getCoupons(QuerySpec querySpec) {
        ItemCollection<QueryOutcome> collection =
                couponTable.getIndex(OWNER_INDEX).query(querySpec);

        List<Coupon> coupons = mapQueryOutcomeToCoupons(collection);

        CouponOwnerLastKey lastKey = getOwnerIndexQueryOutcomeLastKey(collection);

        return Coupons.of(coupons, lastKey);
    }

    private KeyAttribute[] getCouponOwnerLastKeyKeyAttributes(CouponOwnerLastKey couponOwnerLastKey) {
        KeyAttribute[] keys = new KeyAttribute[4];

        keys[0] = new KeyAttribute(Coupon.MAIL, couponOwnerLastKey.getMail());
        keys[1] = new KeyAttribute(Coupon.COUPON_TYPE, couponOwnerLastKey.getCouponType());
        keys[2] = new KeyAttribute(Coupon.OWNER_MAIL, couponOwnerLastKey.getOwnerMail());
        keys[3] = new KeyAttribute(Coupon.COUPON_STATUS, couponOwnerLastKey.getCouponStatus());
        return keys;
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

    private void checkCoupon(Coupon coupon) {
        if (coupon == null) {
            throw new CouponCannotNullException();
        }

        if (coupon.getMail() == null || coupon.getCouponType() == null) {
            throw new CouponPrimaryKeyCannotNullException();
        }
    }

    private List<Coupon> mapQueryOutcomeToCoupons(ItemCollection<QueryOutcome> collection) {
        List<Coupon> coupons = new LinkedList<>();

        collection.forEach(
                (s) -> {
                    coupons.add(getCouponActivityToCoupon(s.toJSON()));
                }
        );

        return Collections.unmodifiableList(coupons);
    }

    private void checkPrimaryKey(Object key) {
        if (key == null) {
            throw new CouponPrimaryKeyCannotNullException();
        }

        if (key instanceof String && ((String)key).length() == 0) {
            throw new CouponPrimaryKeyCannotNullException();
        }
    }

    private Coupon getCoupon(String couponJson) {
        try {
            return JsonUtil.mapToCoupon(couponJson);
        }catch (Exception e) {
            throw new CouponJsonMapException(couponJson);
        }
    }

    private Coupon getCouponActivityToCoupon(String couponJson) {
        Coupon coupon = getCoupon(couponJson);

        try {
            String couponActivity =
                    CouponType.getCouponActivity(coupon.getCouponType())
                            .getCouponActivityMessage();

            return Coupon.of(
                    coupon.getMail(),
                    coupon.getCouponType(),
                    coupon.getOwnerMail(),
                    coupon.getCouponStatus(),
                    couponActivity,
                    coupon.getExpirationTime(),
                    coupon.getOrderDateTime()
            );

        } catch (CouponActivitiesException e) {
            throw new CouponTypeNotAllowedException(coupon.getCouponType());
        }


    }
}
