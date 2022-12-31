package com.example.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/*WebSocket配置类*/
@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {
    //获取httpSession的配置，需要继承 ServerEndpointConfig.Configurator并重写modifyHandshake方法
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        //获取httpsession
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        if (httpSession != null) {
            // 把httpsession放进EndpointConfig作为value，HttpSession.class.getName()作为key
            //在@OnOpen时回调函数会接收到EndpointConfig作为参数，这样就能通过key读取到httpsession了
            sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
        }
        super.modifyHandshake(sec, request, response);
    }
    /**
     * 注入ServerEndpointExporter，
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
