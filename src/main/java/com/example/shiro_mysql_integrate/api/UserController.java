package com.example.shiro_mysql_integrate.api;


import com.example.shiro_mysql_integrate.dao.UserDao;
import com.example.shiro_mysql_integrate.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/index")
    //homepage
    public String index() {
        return "index";
    }

    @RequestMapping("/users")
    @ResponseBody
    @RequiresPermissions("user:query")
    public List<User> getUsers() {
        return (List<User>) userDao.findAll();
    }

    public String login() {
        Subject subject = SecurityUtils.getSubject();

        if (subject.isAuthenticated()) {
            //如果认证成功就重定向到homepage
            return "redirect:/index";
        } else {
            //否则继续回到登录页面
            return "redirect:/login";
        }
    }

    public String logout(){
        SecurityUtils.getSubject().logout();
        //重新回到登录首页
        return "redirect://login";
    }

}
