package com.example.shiro_mysql_integrate.dao;

import com.example.shiro_mysql_integrate.model.User;
import org.springframework.data.repository.CrudRepository;

//是指对User这个表进行操作
public interface UserDao extends CrudRepository<User, Long> {

    //按照这个规范写，spring会帮我们实现
    public User findUserByName(String username);
}
