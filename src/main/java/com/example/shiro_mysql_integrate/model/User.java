package com.example.shiro_mysql_integrate.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    //用户名不能重复
    private String username;

    @Column(nullable = false)
    //密码可以重复
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    //for example, like admin
    private Role role;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    //作用是不把role序列化出来
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<String> getRoleNames(){
        Set<String> roleNames=new HashSet<>();
        roleNames.add(getRole().getName());//因为一个人只能对应一个role，所以add一次就行
        return roleNames;
    }
}
