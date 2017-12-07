package com.cenxui.tea.app.application;

import com.cenxui.tea.app.repositories.user.User;
import com.cenxui.tea.app.services.util.Header;
import com.cenxui.tea.app.util.Http;
import com.cenxui.tea.app.util.JsonUtil;
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
        headers.put(Header.MAIL, "mia@gmail.com");
        Http.get(url, headers);
    }

    @Test
    public void updateUser() {
        Map<String, String> headers = new TreeMap<>();
        headers.put(Header.MAIL, "cenxuilin@gmail.com");

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
