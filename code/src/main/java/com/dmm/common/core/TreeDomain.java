package com.dmm.common.core;

/**
 * Created by cremin on 2017/8/15.
 */
public class TreeDomain extends BaseDomain {

    //默认排序值
    public static Long DEFULAT_SORT_VALUE = 1L;

    protected String parentId;

    protected String parentIds;

    protected Long sort;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }
}
