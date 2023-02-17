package com.example.shiro_mysql_integrate;

import com.example.shiro_mysql_integrate.dao.UserDao;
import com.example.shiro_mysql_integrate.mapper.RoleMapper;
import com.example.shiro_mysql_integrate.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("authorizer")
//这个class的作用是告诉shiro怎么做验证
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    //这个的作用是对用户的权限做一些检查
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = userDao.findUserByName(principalCollection.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(user.getRoleNames());
        simpleAuthorizationInfo.setStringPermissions(roleMapper.getPermissionByRoleId(user.getRole().getId()));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userDao.findUserByName(token.getUsername());
        if(user==null){
            throw new UnknownAccountException();
        }
        //下面这行是将用户名和密码传进去，帮我们做认证，看用户名密码对不对，getName()是把realm的name也传进去
        return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getName());
    }
}
