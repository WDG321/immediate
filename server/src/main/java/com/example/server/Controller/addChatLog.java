package com.example.server.Controller;

import com.example.server.entity.user;
import com.example.server.mappers.userMapper;
import com.example.server.util.ObjectMapperUtil;
import com.example.server.util.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*把聊天记录添加到数据库*/
@Controller
public class addChatLog {
    @ResponseBody
    @RequestMapping(value = "/addChatLogApi", method = RequestMethod.POST)
    public boolean addChatLogApi(HttpServletRequest request, @RequestParam("message") String message) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        //参数设置为false的话就不会创建新的，而是返回null
        HttpSession Session = request.getSession(false);
        System.out.println(message);
        //判断是否登录或者是否登录过期
        if (Session != null) {
            //获取保存在session里的用户信息
            user userObj = (user) Session.getAttribute("userObj");
            //通过工具类获取sqlSession对象
            org.apache.ibatis.session.SqlSession sqlSession = SqlSession.getSqlSession();
            //通过代理模式创建UserMapper接口的代理实现类对象
            userMapper mapper = sqlSession.getMapper(userMapper.class);
            //获取自己数据库里的聊天记录
            String userChatLog = mapper.queryChatLog(userObj.getId());
            //把自己的聊天记录从json转为HashMap
            HashMap userChatLogHashMap = ObjectMapperUtil.toObj(userChatLog, HashMap.class);
            //获取客户端传来的新的聊天记录并转为HashMap
            HashMap<String, ArrayList> chatLog = ObjectMapperUtil.toObj(message, HashMap.class);
            //使用循环进行添加
            for (Map.Entry<String, ArrayList> entry : chatLog.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                ArrayList o = (ArrayList) userChatLogHashMap.get(String.valueOf(entry.getKey()));
                System.out.println("o：" + o);
                //为null则代表第一次发消息
                if (o != null) {
                    //向聊天记录后面添加新的聊天记录
                    o.addAll(entry.getValue());
                } else {
                    //添加新的聊天记录
                    userChatLogHashMap.put(entry.getKey(), entry.getValue());
                    System.out.println("userChatLogHashMap" + userChatLogHashMap);
                }
            }
            //把聊天记录转回json
            String s = ObjectMapperUtil.toJSON(userChatLogHashMap);
            //向数据库中添加聊天记录
            mapper.addChatLog(userObj.getId(), s);
            return true;
        } else {
            /*未登录就返回false*/
            return false;
        }
    }
}
