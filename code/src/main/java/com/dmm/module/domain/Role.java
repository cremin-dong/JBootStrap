package com.dmm.module.domain;

import com.dmm.common.core.BaseDomain;
import org.hibernate.validator.constraints.NotEmpty;

public class Role extends BaseDomain {


    /** 系统内置 **/
    public static String IS_SYS_TRUE = "1";

    /** 非系统内置 **/
    public static String IS_SYS_FLASE = "0";



    @NotEmpty(message="角色名称不能为空")
    private String name;

    @NotEmpty(message="角色描述不能为空")
    private String description;

    private String isSys;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys == null ? null : isSys.trim();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", isSys=").append(isSys);
        sb.append(", createBy=").append(createBy);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", remarks=").append(remarks);
        sb.append(", delFlag=").append(delFlag);
        sb.append("]");
        return sb.toString();
    }
}