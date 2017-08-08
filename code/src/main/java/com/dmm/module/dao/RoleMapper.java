package com.dmm.module.dao;

import com.dmm.common.core.BaseMapper;
import com.dmm.module.domain.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID获取用户拥有的角色
     * @param userId
     * @return
     */
    List<Role> selectByUserId(String userId);
}