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

    //根据id查询用户名和头像地址，用于添加联系人前的查询用户
    user idQueryUser(int id);

    //根据id查询用户除密码联系人聊天记录外的信息
    user idQueryUser2(int id);

    //根据id查询用户的联系人列表
    String idQueryContact(int id);

    //注册用户
    int registered(user user);
}
