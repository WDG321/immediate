package com.example.server.Controller;

import com.example.server.entity.user;
import com.example.server.mappers.userMapper;
import com.example.server.util.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/*返回个人信息*/
@Controller
public class me {
    @ResponseBody
    @RequestMapping(value = "/meApi", method = RequestMethod.POST)
    public user meApi(HttpServletRequest request) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        //参数设置为false的话就不会创建新的，而是返回null
        HttpSession Session = request.getSession(false);
        //判断是否登录或者是否登录过期
        if (Session != null) {
            //从Session获取数据
            user userObj = (user) Session.getAttribute("userObj");
            //通过工具类获取sqlSession对象
            org.apache.ibatis.session.SqlSession sqlSession = SqlSession.getSqlSession();
            //通过代理模式创建UserMapper接口的代理实现类对象
            userMapper mapper = sqlSession.getMapper(userMapper.class);
            //从数据库查询信息返回
            return mapper.queryUser(userObj.getUsername());
        } else {
            /*未登录就返回null*/
            return null;
        }
    }
}
