package com.dmm.module.service;

import com.dmm.common.core.AbstractBaseService;
import com.dmm.module.dao.UserMapper;
import com.dmm.module.domain.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cremin on 2017/7/31.
 */
@Service
public class UserService extends AbstractBaseService<User> {


    @Autowired
    private UserMapper userMapper;

    public User selectByUserName(String username){

        User user = new User();
        user.setUsername(username);

        List<User> users = userMapper.selectBySelective(user);

        if(CollectionUtils.isNotEmpty(users)){
            return users.get(0);
        }

        return  null;
    }
}
