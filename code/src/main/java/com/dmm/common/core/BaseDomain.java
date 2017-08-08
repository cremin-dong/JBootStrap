package com.dmm.common.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Domain基础类
 * Created by cremin on 2017/7/31.
 */
public class BaseDomain extends AbstractDomain implements Serializable{

    public static final String DEL_FLAG_YES = "1";
    public static final String DEL_FLAG_NO = "0";

    protected String remarks;	// 备注
    protected String createBy;	// 创建者
    protected Date createDate;	// 创建日期
    protected String updateBy;	// 更新者
    protected Date updateDate;	// 更新日期
    protected String delFlag; 	// 删除标记（0：正常；1：删除；）

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


    /**
     * 插入之前执行方法，需要手动调用
     */
    public void preInsert(){

        this.setId(UUID.randomUUID().toString());
        this.updateDate = new Date();
        this.createDate = this.updateDate;
        this.updateBy = this.createBy;
        this.setDelFlag(BaseDomain.DEL_FLAG_NO);

    }

}
