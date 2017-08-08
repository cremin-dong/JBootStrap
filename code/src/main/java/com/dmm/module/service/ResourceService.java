package com.dmm.module.service;

import com.dmm.common.core.AbstractBaseService;
import com.dmm.module.dao.ResourceMapper;
import com.dmm.module.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cremin on 2017/8/2.
 */
@Service
public class ResourceService extends AbstractBaseService<Resource> {


    @Autowired
    private ResourceMapper resourceMapper;
    /**
     * 根据用户ID获取用户拥有的权限
     * @param userId
     * @return
     */
    public List<Resource> selectByUserId(String userId){

        return resourceMapper.selectByUserId(userId);
    }
}
