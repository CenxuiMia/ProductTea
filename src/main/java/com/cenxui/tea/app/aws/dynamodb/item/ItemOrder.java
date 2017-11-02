package com.cenxui.tea.app.aws.dynamodb.item;

import com.cenxui.tea.app.repositories.order.Order;
import lombok.Value;

/**
 * used to map dynamodb data to order
 */
@Value public class ItemOrder {
    Order Item; // must be named 'Item' ,since dynamodb used Item as its item name;
}
