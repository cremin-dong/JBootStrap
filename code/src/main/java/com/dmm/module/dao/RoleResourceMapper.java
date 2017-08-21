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
     * 根据资源ID删除
     * @param resourceId
     */
    void deleteByResourceId(String resourceId);


    /**
     * 批量保存
     * @param roleResourceList
     */
    void saveBatch(List<RoleResource> roleResourceList);

}