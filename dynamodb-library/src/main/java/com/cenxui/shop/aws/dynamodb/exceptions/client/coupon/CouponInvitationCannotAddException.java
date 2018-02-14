package com.cenxui.shop.aws.dynamodb.exceptions.client.coupon;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

public class CouponInvitationCannotAddException extends RepositoryClientException {
    public CouponInvitationCannotAddException(String invitationMail) {
        super(String.format("Invitation coupon invitationMail cannot equal mail :%s", invitationMail));
    }
}
