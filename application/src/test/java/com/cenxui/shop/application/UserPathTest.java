package com.cenxui.shop.application;

import com.cenxui.shop.repositories.user.User;
import com.cenxui.shop.util.Http;
import com.cenxui.shop.util.JsonUtil;
import com.cenxui.shop.web.app.services.util.Header;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class UserPathTest {

    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9001/user";
    }

    @Test
    public void getUser() {
        Map<String, String> headers = new TreeMap<>();
        headers.put(Header.MAIL, "cenxui@gmail.com");
        Http.get(url, headers);
    }

    @Test
    public void updateUser() {
        Map<String, String> headers = new TreeMap<>();
        headers.put(Header.MAIL, "cenxui@gmail.com");

        User user = User.of(
                true,
                "Hung",
                "Mia",
                "cenxuilin@gmail.com",
                "a",
                "124555"
        );

        String body = JsonUtil.mapToJson(user);
        Http.post(url, body, headers);
    }

}
