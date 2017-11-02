package com.cenxui.tea.app.aws.dynamodb.item;

import com.cenxui.tea.app.repositories.product.Product;
import lombok.Value;

/**
 * used to map dynamodb data to product
 */
@Value public class ItemProduct {
    Product Item; // must be named 'Item' ,since dynamodb used Item as its item name;
}
