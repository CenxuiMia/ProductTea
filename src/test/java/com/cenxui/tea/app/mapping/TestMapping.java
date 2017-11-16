package com.cenxui.tea.app.mapping;

import com.cenxui.tea.app.repositories.order.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

@RunWith(JUnit4.class)
public class TestMapping {
    @Test
    public void testMapping() {
        String body = "{\"purchaser\":\"23rvnkfvfk\",\"mail\":\"2rj4if\",\"phone\":\"mdmffmlv\",\"receiver\":\"dmkmfvk\",\"address\":\"3kfldml\",\"products\":[\"mdcmlq\"],\"money\":\"1222333\",\"comment\":\"103fmc\"}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.readValue(body, Order.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
