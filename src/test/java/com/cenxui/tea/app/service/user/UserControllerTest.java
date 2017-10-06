package com.cenxui.tea.app.service.user;

import com.cenxui.tea.app.service.user.service.MockUserService;
import com.cenxui.tea.app.util.ControllerServiceUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserControllerTest {

    @Before
    public void setUp() throws Exception {
        ControllerServiceUtil.mockControllerService(UserController.class, "service", new MockUserService());
    }


    @Test
    public void authenticateByUserName() throws Exception {
        System.out.println(UserController.authenticateByUserName("userName", "password"));
    }

    @Test
    public void authenticateByMail() throws Exception {
        System.out.println(UserController.authenticateByMail("mail", "password"));
    }

}