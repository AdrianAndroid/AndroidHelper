package com.proxy;

public class UserServiceImpl implements UserService{
    @Override
    public String getUserByName(String name) {
        System.out.println("从数据库中查询到："+name);
        return name;
    }
}
