package com.akku.controller;

import com.akku.pojo.User;
import com.akku.service.UserService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import static com.akku.common.realm.AuthRealm.SESSION_USER_KEY;

/**
 * Created by akku on 2017/6/15.
 */

@Controller
@Api
@RequestMapping("/shiro")
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public Object login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
//        NutMap re = new NutMap();
//        int userId = userService.fetch(username, password);
//        if (userId < 0) {
//            return re.setv("ok", false).setv("msg", "用户名或密码错误");
//        } else {
//            session.setAttribute(SESSION_USER_KEY, userId);
//            // 完成nutdao_realm后启用.
//            // SecurityUtils.getSubject().login(new SimpleShiroToken(userId));
//            return re.setv("ok", true);
//        }

        NutMap re = new NutMap();
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        String info = loginUser(user);
        if (!"SUCC".equals(info)) {
            return re.setv("ok", false).setv("msg", "用户名或密码错误");
        }else{
            return re.setv("ok", true);
        }
    }
    private String loginUser(User user) {
        if (isRelogin(user)) return "SUCC"; // 如果已经登陆，无需重新登录

        return shiroLogin(user); // 调用shiro的登陆验证
    }
    private boolean isRelogin(User user) {
        Subject us = SecurityUtils.getSubject();
        if (us.isAuthenticated()) {
            return true; // 参数未改变，无需重新登录，默认为已经登录成功
        }
        return false; // 需要重新登陆
    }
    private String shiroLogin(User user) {
        // 组装token，包括客户公司名称、简称、客户编号、用户名称；密码
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword().toCharArray(), null);
        token.setRememberMe(true);

        // shiro登陆验证
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException ex) {
            return "用户不存在或者密码错误！";
        } catch (IncorrectCredentialsException ex) {
            return "用户不存在或者密码错误！";
        } catch (AuthenticationException ex) {
            return ex.getMessage(); // 自定义报错信息
        } catch (Exception ex) {
            ex.printStackTrace();
            return "内部错误，请重试！";
        }
        return "SUCC";
    }
}