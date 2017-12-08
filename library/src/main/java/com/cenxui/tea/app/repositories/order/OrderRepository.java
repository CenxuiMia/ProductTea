package com.cenxui.tea.app.repositories.order;

public interface OrderRepository extends OrderBaseRepository {

    Order trialOrder(Order order);

}
