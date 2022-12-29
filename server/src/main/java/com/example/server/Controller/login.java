package com.example.server.Controller;

import com.example.server.mappers.user;
import com.example.server.mappers.userMapper;
import com.example.server.sqlSession.getSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class login {
    //@CrossOrigin用于设置允许跨域,但无法解决cookie问题
    //@CrossOrigin()
    @ResponseBody
    @RequestMapping(value = "/loginApi", method = RequestMethod.POST)
    public boolean loginApi(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) {
        //通过工具类获取sqlSession对象
        SqlSession sqlSession = getSqlSession.getSqlSession();
        //通过代理模式创建UserMapper接口的代理实现类对象
        userMapper mapper = sqlSession.getMapper(userMapper.class);
        //通过mybatis查询数据库
        user userObj = mapper.login(username, password);
        if (userObj == null) {
            //为null则表示数据库没有匹配的数据
            //代表登陆失败
            return false;
        } else {
            //获取Session
            HttpSession Session = request.getSession();
            //判断是否是新的Session
            if (!Session.isNew()) {
                //杀死旧的Session,以免浪费内存
                Session.invalidate();
                //重新给个Session
                Session = request.getSession();
            }
            //添加数据
            Session.setAttribute("username", userObj.getUsername());
            Session.setAttribute("id", userObj.getId());
            Session.setAttribute("profilePhoto", userObj.getProfilePhoto());
            //获取数据
            System.out.println("Session最大有效期为" + Session.getServletContext().getSessionTimeout() + "分钟");
            return true;
        }
    }
}
