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

/*把聊天记录添加到数据库*/
@Controller
public class addChatLog {
    @ResponseBody
    @RequestMapping(value = "/addChatLogApi", method = RequestMethod.POST)
    public boolean addChatLogApi(HttpServletRequest request, @RequestParam("message") String message) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        //参数设置为false的话就不会创建新的，而是返回null
        HttpSession Session = request.getSession(false);
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
            //把聊天记录从json转为ArrayList
            ArrayList userChatLogArrayList = ObjectMapperUtil.toObj(userChatLog, ArrayList.class);
            //获取客户端传来的新的聊天记录并转为ArrayList集合
            ArrayList chatLog = ObjectMapperUtil.toObj(message, ArrayList.class);
            //使用循环进行添加
            for (int i = 0; i < chatLog.size(); i++) {
                //把新的记录添加到旧的里面
                //add方法：如果 index 没有传入实际参数，元素将追加至数组的最末尾。
                userChatLogArrayList.add(chatLog.get(i));
            }
            //把聊天记录转为json
            String s = ObjectMapperUtil.toJSON(userChatLogArrayList);
            //向数据库中添加聊天记录
            mapper.addChatLog(userObj.getId(), s);
            return true;
        } else {
            /*未登录就返回false*/
            return false;
        }
    }
}
