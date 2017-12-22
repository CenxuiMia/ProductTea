package com.cenxui.shop.repositories.order;

public interface OrderRepository extends OrderBaseRepository {

    Order trialOrder(Order order);

}
