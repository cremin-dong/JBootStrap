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
}