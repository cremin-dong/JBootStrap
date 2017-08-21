package com.dmm.module.service;

import com.dmm.common.core.AbstractBaseService;
import com.dmm.module.dao.RoleMapper;
import com.dmm.module.dao.RoleResourceMapper;
import com.dmm.module.dao.UserRoleMapper;
import com.dmm.module.domain.Resource;
import com.dmm.module.domain.Role;
import com.dmm.module.domain.RoleResource;
import com.dmm.module.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cremin on 2017/8/1.
 */
@Service
public class RoleService extends AbstractBaseService<Role> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 根据用户ID获取用户拥有的角色
     * @param userId
     * @return
     */
    public List<Role> selectByUserId(String userId){

        return roleMapper.selectByUserId(userId);
    }

    /**
     * 保存分配角色信息
     * @param userId 用户ID
     * @param roleIdList 角色ID列表
     */
    @Transactional
    public void saveAssignRoles(String userId, String[] roleIdList) {

        //删除原有
        userRoleMapper.deleteByUserId(userId);

        //新增的角色资源列表
        List<UserRole> userRoleList = new ArrayList<>();

        if(roleIdList != null && roleIdList.length > 0){

            Arrays.stream(roleIdList).forEach(roleId -> {

                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);

                userRoleList.add(userRole);

            });

            userRoleMapper.saveBatch(userRoleList);
        }

    }

}
