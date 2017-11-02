package com.cenxui.tea.app.aws.dynamodb.item;

import com.cenxui.tea.app.repositories.user.User;
import lombok.Getter;
import lombok.Value;

/**
 * used to map dynamodb data to user
 */
@Value public class ItemUser {
    User user; // must be named 'Item' ,since dynamodb used Item as its item name;
}
