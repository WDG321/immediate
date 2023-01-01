package com.example.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //
@NoArgsConstructor  //无参构造
@AllArgsConstructor //有参构造
public class idOrMessage {
    private int id;//需要向哪个联系人发消息的id
    private String message;
}
