package com.dmm.module.dao;

import com.dmm.common.core.BaseMapper;
import com.dmm.module.domain.Resource;

import java.util.List;

public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 根据用户ID获取用户拥有的权限
     * @param userId
     * @return
     */
    List<Resource> selectByUserId(String userId);


    /**
     * 删除菜单及所有子菜单项
     * @param id
     */
    void delResourceAndChilds(String id);

    /**
     * 根据角色ID获取角色拥有的资源
     * @param roleId
     * @return
     */
    List<Resource> selectByRoleId(String roleId);
}