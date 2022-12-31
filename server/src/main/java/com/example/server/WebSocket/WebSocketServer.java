package com.example.server.WebSocket;

import com.example.server.WebSocketConfig;
import com.example.server.entity.contactIdOrMessage;
import com.example.server.entity.user;
import com.example.server.util.ObjectMapperUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
/*@ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
    注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端*/
@ServerEndpoint(value = "/websocket", configurator = WebSocketConfig.class)  // 接口路径 ws://localhost/webSocket;
public class WebSocketServer {
    //存放客户端连接对象的集合,使用线程安全的concurrent包下的set
    private static final ConcurrentHashMap<Session, Integer> SessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        /*思路：连接成功后把session的id保存起来并绑定用户，断开后就赋值null，以此来判断是否在线，以及指定用户发送消息*/
        //获取httpSession
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println("httpSession的id：" + httpSession.getId());
        //把客户端连接对象与httpSession里面储存的id绑定存入map
        user userObj = (user) httpSession.getAttribute("userObj");
        SessionMap.put(session, userObj.getId());
        //getBasicRemote()为同步发送,需要等待上一条发送完毕才能接着发送，就像排队上厕所
        //session.getBasicRemote().sendText("恭喜连接成功"); //向该Session连接的用户发送字符串数据。
        //getAsyncRemote()为异步发送,无需等待，直接发送，通常都是使用这种方式
        session.getAsyncRemote().sendText("恭喜连接成功"); //向该Session连接的用户发送字符串数据。
        //.size()方法用于获取set数据结构的值的个数
        System.out.println("【websocket消息】有新的连接, 总数:{" + SessionMap.size() + "}");
        SessionMap.forEach((k, v) -> System.out.println("key = " + k + ", value = " + v));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        //清除保存的连接
        SessionMap.remove(session);
        System.out.println("【websocket消息】连接断开, 总数:{" + SessionMap.size() + "}");
    }

    //发生错误时调用
    @OnError
    public void onError(Throwable error) {
        System.out.println("发生错误了：" + error);
    }

    /**
     * 收到客户端消息后调用的方法
     * message为客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("【websocket消息】收到客户端发来的消息:{" + message + "}");
        //把消息序列化为对象
        contactIdOrMessage messageObj = ObjectMapperUtil.toObj(message, contactIdOrMessage.class);
        //发送消息给指定用户
        boolean b = designatedIdSend(messageObj.getContactId(), messageObj.getMessage());
        //处理用户不在线的情况
        if (!b) {
            System.out.println("该用户不在线,发送失败");
        }
    }

    //定义一个指定id发送信息的方法,用户不在线就返回false
    private boolean designatedIdSend(int id, String message) {
        //entrySet();返回当前map对象中所有key-value对构成的Set集合
        for (Map.Entry<Session, Integer> entry : SessionMap.entrySet()) {
            if (entry.getValue() == id) {
                entry.getKey().getAsyncRemote().sendText(message);
                return true;
            }
        }
        return false;
    }
}