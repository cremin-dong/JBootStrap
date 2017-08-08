package com.dmm.module.service;

import com.dmm.common.core.AbstractBaseService;
import com.dmm.module.dao.RoleMapper;
import com.dmm.module.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cremin on 2017/8/1.
 */
@Service
public class RoleService extends AbstractBaseService<Role> {

    @Autowired
    private  RoleMapper roleMapper;

    /**
     * 根据用户ID获取用户拥有的角色
     * @param userId
     * @return
     */
    public List<Role> selectByUserId(String userId){

        return roleMapper.selectByUserId(userId);
    }
}
