package com.dmm.common.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by cremin on 2017/7/31.
 */
public abstract class AbstractBaseService<T extends AbstractDomain> {


    @Autowired
    protected BaseMapper<T> mapper;

    @Transactional
    public int deleteByPrimaryKey(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public int insert(T record) {
        return mapper.insert(record);
    }

    @Transactional
    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }

    public T selectByPrimaryKey(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    public List<T> selectBySelective(T record) {
        return mapper.selectBySelective(record);
    }

    public List<T> selectByMap(Map<String, Object> map) {
        return mapper.selectByMap(map);
    }

}
