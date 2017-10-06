package com.cenxui.tea.app.service.user;

import com.cenxui.tea.app.service.user.repository.MockUserRepository;
import com.cenxui.tea.app.util.ControllerManagerUtil;
import org.junit.Before;
import org.junit.Test;

public class UserControllerTest {

    @Before
    public void setUp() throws Exception {
        ControllerManagerUtil.mockControllerManager(UserController.class, "manager", new MockUserRepository());
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