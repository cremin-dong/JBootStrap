package com.dmm.module.domain;

import com.dmm.common.core.BaseDomain;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User extends BaseDomain {


    //默认密码
    public static  final String DEFAULT_PASSWORD = "11111111";

    //性别：男
    public static  final String SEX_MAN = "m";

    //性别：女
    public static  final String SEX_FALE = "f";

    private String companyId;

    private String officeId;

    @NotEmpty(message="用户名不能为空")
    private String username;

    private String password;

    @NotEmpty(message="工号不能为空")
    private String no;

    private String name;

    @Email(message="邮件格式错误")
    private String email;

    private String phone;

    private String mobile;

    private String photo;

    private String sex;

    private String loginIp;

    private Date loginDate;

    private String loginFlag;

    private List<Role> roleList = null; // 拥有角色列表

    private List<Resource> resourceList = null; // 拥有权限列表


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId == null ? null : officeId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag == null ? null : loginFlag.trim();
    }

    public List<Role> getRoleList() {
        if(roleList == null){
            roleList = new ArrayList<>();
        }
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Resource> getResourceList() {
        if(resourceList == null){
            resourceList = new ArrayList<>();
        }
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", companyId=").append(companyId);
        sb.append(", officeId=").append(officeId);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", no=").append(no);
        sb.append(", name=").append(name);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", mobile=").append(mobile);
        sb.append(", photo=").append(photo);
        sb.append(", sex=").append(sex);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", loginDate=").append(loginDate);
        sb.append(", loginFlag=").append(loginFlag);
        sb.append(", remarks=").append(remarks);
        sb.append(", createBy=").append(createBy);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", delFlag=").append(delFlag);
        sb.append("]");
        return sb.toString();
    }
}