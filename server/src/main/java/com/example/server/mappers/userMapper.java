package com.example.server.mappers;

import com.example.server.entity.user;

public interface userMapper {
    //根据账户名与密码查询用户信息,用于登录
    user login(String username, String password);

    //添加联系人
    int addContact(String username, String contact);

    //根据用户名查询用户
    user queryUser(String username);

    //根据id添加聊天记录
    int addChatLog(int id, String chatLog);

    //根据id查询用户的聊天记录
    String queryChatLog(int id);
}
