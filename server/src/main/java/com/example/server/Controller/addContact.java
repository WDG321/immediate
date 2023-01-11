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

/*处理添加好友*/
@Controller
public class addContact {
    //在添加之前查询用户
    @ResponseBody
    @RequestMapping(value = "/idQueryUserApi", method = RequestMethod.POST)
    public user idQueryUserApi(HttpServletRequest request, @RequestParam("id") int id) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        //参数设置为false的话就不会创建新的，而是返回null
        HttpSession Session = request.getSession(false);
        //判断是否登录或者是否登录过期
        if (Session != null) {
            //通过工具类获取sqlSession对象
            org.apache.ibatis.session.SqlSession sqlSession = SqlSession.getSqlSession();
            //通过代理模式创建UserMapper接口的代理实现类对象
            userMapper mapper = sqlSession.getMapper(userMapper.class);
            System.out.println(mapper.idQueryUser(id));
            return mapper.idQueryUser(id);
        } else {
            return null;
        }
    }

    //添加用户
    @ResponseBody
    @RequestMapping(value = "/addContactApi", method = RequestMethod.POST)
    public boolean addContactApi(HttpServletRequest request, @RequestParam("id") int id) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        //参数设置为false的话就不会创建新的，而是返回null
        HttpSession Session = request.getSession(false);
        //判断是否登录或者是否登录过期
        if (Session != null) {
            //通过工具类获取sqlSession对象
            org.apache.ibatis.session.SqlSession sqlSession = SqlSession.getSqlSession();
            //通过代理模式创建UserMapper接口的代理实现类对象
            userMapper mapper = sqlSession.getMapper(userMapper.class);
            //获取自己的用户信息
            user myUser = (user) Session.getAttribute("userObj");
            //查询要添加用户
            user userObj = mapper.idQueryUser2(id);
            //获取当前用户的联系人列表信息,并转为list
            String contact = mapper.idQueryContact(myUser.getId());//注册时记得把联系人列表信息设置为[],不然以后会出bug
            ArrayList contactObj = ObjectMapperUtil.toObj(contact, ArrayList.class);
            /*如果联系人存在则放弃添加*/
            for (int i = 0; i < contactObj.size(); i++) {
                String s = ObjectMapperUtil.toJSON(contactObj.get(i));
                user user = ObjectMapperUtil.toObj(s, user.class);
                if (user.getId() == id) {
                    return false;
                }
            }
            //向list中添加数据
            contactObj.add(userObj);
            //把list转为json
            String contactJson = ObjectMapperUtil.toJSON(contactObj);
            System.out.println(contactJson);
            //把查询到的用户添加到联系人
            int i = mapper.addContact(myUser.getUsername(), contactJson);
            if (i > 0) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }
}
