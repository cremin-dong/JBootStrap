package com.dmm.common.core;

import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * Created by cremin on 2017/8/1.
 */
public abstract class AbstractDomain implements Serializable {

    protected String id;   //主键

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

}
