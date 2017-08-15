package com.dmm.common.shiro;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.dmm.module.domain.Role;
import com.dmm.module.domain.User;
import com.dmm.module.service.ResourceService;
import com.dmm.module.service.RoleService;
import com.dmm.module.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Shiro authentication & authorization realm that relies on
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private ResourceService resourceService;

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {

        final UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        final String username = usernamePasswordToken.getUsername();

        if (username == null) {
            throw new UnknownAccountException("未知账户");
        }

        final User user = userService.selectByUserName(username);

        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误");
        }

        // 认证的实体信息
        Object principal = username;
        // 密码
        Object credentials = user.getPassword().toCharArray();
        // 盐值：取用户信息中唯一的字段来生成盐值，避免由于两个用户原始密码相同，加密后的密码也相同
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        // 当前realm对象的name，调用父类的getName()方法即可
        String realmName = getName();

        return new SimpleAuthenticationInfo(username, credentials, credentialsSalt, realmName);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            final PrincipalCollection principals) {

        // retrieve role names and permission names
        final String username = (String) principals.getPrimaryPrincipal();

        final User user = userService.selectByUserName(username);

        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }


        final Set<String> roleNameSet = new LinkedHashSet<>();
        final Set<String> permissionNameSet = new LinkedHashSet<>();


        //根据用户ID获取用户拥有的角色
        List<Role> roles = roleService.selectByUserId(user.getId());

        //根据用户ID获取用户拥有的权限
        List<com.dmm.module.domain.Resource> resourceList = resourceService.selectByUserId(user.getId());

        roles.forEach(item -> {
            roleNameSet.add(item.getName());
        });

        resourceList.forEach(item -> {
            permissionNameSet.add(item.getPermission());
        });


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNameSet);
        info.setStringPermissions(permissionNameSet);

        return info;
    }

}