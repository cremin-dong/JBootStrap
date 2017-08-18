package com.dmm.module.dao;

import com.dmm.common.core.BaseMapper;
import com.dmm.module.domain.RoleResource;

import java.util.List;

public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    /**
     * 根据角色ID删除
     * @param roleId
     */
    void deleteByRoleId(String roleId);


    /**
     *
     * @param roleResourceList
     */
    void saveBatch(List<RoleResource> roleResourceList);

}