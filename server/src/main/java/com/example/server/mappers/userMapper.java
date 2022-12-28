package com.example.server.mappers;

public interface userMapper {
    //根据账户名与密码查询用户信息
    user login(String username, String password);
}
