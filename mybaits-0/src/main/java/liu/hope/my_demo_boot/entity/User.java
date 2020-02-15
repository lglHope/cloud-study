package liu.hope.my_demo_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * 用户类
 */
public class User {

    @JsonIgnore  //jasonIgnore返回时用来在直接返回忽略一些字段
    private long id;

    private String name;

    @JsonIgnore
    private String password;

    private List<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}