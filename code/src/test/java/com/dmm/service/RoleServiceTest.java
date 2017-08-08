package com.dmm.service;

import com.dmm.JbootstrapApplication;
import com.dmm.module.domain.Role;
import com.dmm.module.service.RoleService;
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
public class RoleServiceTest {


    @Autowired
    RoleService roleService;


    @Test
    @Transactional
    public void testAdd(){

        Role role = new Role();

        role.setIsSys("1");
        role.setName("user");
        role.setDescription("普通用户");
        role.setCreateBy("xxxxxxxxxx");
        role.preInsert();

        int insert = roleService.insert(role);

        Assert.assertEquals(1,insert);

    }
}
