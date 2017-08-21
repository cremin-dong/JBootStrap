package com.dmm.module.dao;

import com.dmm.common.core.BaseMapper;
import com.dmm.module.domain.UserRole;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据角色ID删除
     * @param roleId
     */
    void deleteByRoleId(String roleId);


    /**
     * 根据用户ID删除
     * @param userId
     */
    void deleteByUserId(String userId);


    /**
     * 批量保存
     * @param userRoleList
     */
    void saveBatch(List<UserRole> userRoleList);

}