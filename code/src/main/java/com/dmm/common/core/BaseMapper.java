package com.dmm.common.core;

import java.util.List;
import java.util.Map;

/**
 * Created by cremin on 2017/7/31.
 */
public interface BaseMapper<T extends AbstractDomain> {

    int deleteByPrimaryKey(String id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    List<T> selectBySelective(T record);

    List<T> selectByMap(Map<String, Object> map);
}
