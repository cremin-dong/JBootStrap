package com.dmm.module.domain;

import com.dmm.common.core.TreeDomain;
import org.hibernate.validator.constraints.NotEmpty;


public class Resource extends TreeDomain {

    public static final String TYPE_MENU = "0"; //菜单

    public static final String TYPE_OPERATOR = "1"; //操作

    @NotEmpty(message="名称不能为空")
    private String name;

    private String href;

    private String icon;

    @NotEmpty(message="类型不能为空")
    private String type;

    @NotEmpty(message="权限不能为空")
    private String permission;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href == null ? null : href.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", parentIds=").append(parentIds);
        sb.append(", name=").append(name);
        sb.append(", sort=").append(sort);
        sb.append(", href=").append(href);
        sb.append(", icon=").append(icon);
        sb.append(", type=").append(type);
        sb.append(", permission=").append(permission);
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