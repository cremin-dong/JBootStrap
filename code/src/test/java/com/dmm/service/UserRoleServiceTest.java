package com.dmm.service;

import com.dmm.JbootstrapApplication;
import com.dmm.module.domain.UserRole;
import com.dmm.module.service.UserRoleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cremin on 2017/8/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JbootstrapApplication.class)
public class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    @Transactional
    public void testAdd(){

        UserRole userRole = new UserRole();
        userRole.setRoleId("eb539ca1-8087-4fb9-8646-a29479ed5748");
        userRole.setUserId("8f0fb42d-8bf7-4844-afda-c15c8a78b605");

        int insert = userRoleService.insert(userRole);

        Assert.assertEquals(1,insert);

    }
}
