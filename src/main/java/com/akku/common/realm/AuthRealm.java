package com.akku.common.realm;

import com.akku.pojo.Role;
import com.akku.pojo.User;
import com.akku.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akku on 2017/6/15.
 */
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    public static final String SESSION_USER_KEY = "userx";
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(AuthRealm.SESSION_USER_KEY);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Role r : user.getRoles()){
            info.addRole(r.getName().trim());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 把token转换成User对象
        User userLogin = tokenToUser((UsernamePasswordToken) authenticationToken);
        // 验证用户是否可以登录
        int ui = userService.fetch(userLogin.getName(),userLogin.getPassword());
        if(ui < 1)
            return null; // 异常处理，找不到数据
        // 设置session
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(AuthRealm.SESSION_USER_KEY, ui);
        //当前 Realm 的 name
        String realmName = this.getName();
        //登陆的主要信息: 可以是一个实体类的对象, 但该实体类的对象一定是根据 token 的 username 查询得到的.
//      Object principal = ui.getUsername();
        Object principal = authenticationToken.getPrincipal();
        return new SimpleAuthenticationInfo(principal, userLogin.getPassword(), realmName);
    }
    private User tokenToUser(UsernamePasswordToken authcToken) {
        User user = new User();
        user.setName(authcToken.getUsername());
        user.setPassword(String.valueOf(authcToken.getPassword()));
        return user;
    }

}
