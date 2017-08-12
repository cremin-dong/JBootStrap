package com.dmm.common.core;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * DataTablesPager分页对象
 * Created by cremin on 2017/8/9.
 */
public class DataTablesPager<T> extends BackResult{

    private List<T> data;

    private long recordsTotal;

    private long recordsFiltered;

    private int draw;

    public DataTablesPager() {

    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
