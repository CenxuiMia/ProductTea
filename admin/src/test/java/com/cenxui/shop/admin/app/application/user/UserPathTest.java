package com.cenxui.shop.admin.app.application.user;

import com.cenxui.shop.util.Http;
import org.junit.Test;

public class UserPathTest {

    private String url = "http://localhost:9000/admin/user";


    @Test
    public void getAllUsers() {
        Http.get(url);
    }

}
