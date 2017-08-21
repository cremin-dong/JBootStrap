package com.dmm.module.service;

import com.dmm.common.core.AbstractBaseService;
import com.dmm.module.dao.ResourceMapper;
import com.dmm.module.dao.RoleResourceMapper;
import com.dmm.module.domain.Resource;
import com.dmm.module.domain.RoleResource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by cremin on 2017/8/2.
 */
@Service
public class ResourceService extends AbstractBaseService<Resource> {


    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    /**
     * 根据用户ID获取用户拥有的权限
     * @param userId
     * @return
     */
    public List<Resource> selectByUserId(String userId){

        return resourceMapper.selectByUserId(userId);
    }


    /**
     * 删除菜单及所有子菜单项
     * @param id
     */
    @Transactional
    public void delResourceAndChilds(String id) {
        resourceMapper.delResourceAndChilds(id);
        roleResourceMapper.deleteByResourceId(id);
    }


    /**
     * 根据角色ID获取角色拥有的资源
     * @param roleId
     * @return
     */
    public List<Resource> selectByRoleId(String roleId){

        return resourceMapper.selectByRoleId(roleId);
    }


    /**
     * 保存授权信息
     * @param roleId 角色ID
     * @param resourceIdList 资源ID列表
     */
    @Transactional
    public void saveAuthorize(String roleId,String[] resourceIdList){

        //删除原有
        roleResourceMapper.deleteByRoleId(roleId);

        //新增的角色资源列表
        List<RoleResource> resourceList = new ArrayList<>();

        if(resourceIdList != null && resourceIdList.length > 0){

            Arrays.stream(resourceIdList).forEach(resourceId -> {

                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResource.setId(UUID.randomUUID().toString());

                resourceList.add(roleResource);

            });

            roleResourceMapper.saveBatch(resourceList);
        }

    }
}
