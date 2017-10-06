package com.cenxui.tea.app.service.user;

import com.cenxui.tea.app.service.user.repository.MockUserRepository;
import com.cenxui.tea.app.util.ControllerServiceUtil;
import org.junit.Before;
import org.junit.Test;

public class UserControllerTest {

    @Before
    public void setUp() throws Exception {
        ControllerServiceUtil.mockControllerService(UserCoreController.class, "manager", new MockUserRepository());
    }


    @Test
    public void authenticateByUserName() throws Exception {
        System.out.println(UserCoreController.authenticateByUserName("userName", "password"));
    }

    @Test
    public void authenticateByMail() throws Exception {
        System.out.println(UserCoreController.authenticateByMail("mail", "password"));
    }

}