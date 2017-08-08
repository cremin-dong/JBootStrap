package com.dmm.service;

import com.dmm.JbootstrapApplication;
import com.dmm.module.domain.User;
import com.dmm.module.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by cremin on 2017/7/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JbootstrapApplication.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @Transactional
    public void testAdd() {

        User userInfo = new User();
        userInfo.setUsername("xxxxxxx");
        userInfo.setPassword(new SimpleHash("MD5", "123456", userInfo.getUsername(), 2).toString());

        userInfo.setCreateBy("1111111111");
        userInfo.setName(userInfo.getUsername());
        userInfo.setCompanyId("122222222");
        userInfo.setPhone("13222222222");
        userInfo.setLoginDate(new Date());
        userInfo.setLoginFlag("false");
        userInfo.setLoginIp("192.168.1.11");
        userInfo.setMobile("13111111111");
        userInfo.setOfficeId("111111111");
        userInfo.setEmail("122222@qq.com");
        userInfo.setNo("1111111");
        userInfo.setRemarks("备注");
        userInfo.setPhoto("3432432.png");

        userInfo.preInsert();

        int insert = userService.insert(userInfo);

        Assert.assertEquals(1, insert);
    }


    @Test
    public void testSelectByPrimaryKey() {

        User userInfo = userService.selectByPrimaryKey("888559ba-7383-410d-b662-b8d7c8ab48f5");
        Assert.assertNotNull(userInfo);
        Assert.assertEquals("cremin", userInfo.getUsername());

    }

    @Test
    public void testSelectBySelective() {
        User userInfo = new User();
        List<User> users = userService.selectBySelective(userInfo);
        Assert.assertNotNull(users);
    }

    @Test
    public void testSelectBySelectivePage() {

        User userInfo = new User();

        Page<List<User>> listPage = PageHelper.startPage(2, 3)
                .doSelectPage(() -> userService.selectBySelective(userInfo));

        Assert.assertEquals(3, listPage.getPageSize());

    }
}
