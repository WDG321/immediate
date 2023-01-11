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

/*处理用户注册*/
@Controller
public class registered {
    @ResponseBody
    @RequestMapping(value = "/registeredApi", method = RequestMethod.POST)
    public String registeredApi(@RequestParam("registeredInformation") String user) {
        /*把注册信息转为对象*/
        com.example.server.entity.user userObj = ObjectMapperUtil.toObj(user, user.class);
        System.out.println(userObj);
        //通过工具类获取sqlSession对象
        org.apache.ibatis.session.SqlSession sqlSession = SqlSession.getSqlSession();
        //通过代理模式创建UserMapper接口的代理实现类对象
        userMapper mapper = sqlSession.getMapper(userMapper.class);
        //判断数据库中是否有相同昵称的
        if (mapper.queryUser(userObj.getUsername()) == null) {
            //为null代表用户不存在，可以注册
            //注册用户
            if (mapper.registered(userObj) > 0) {
                return "注册成功";
            } else {
                return "数据库错误";
            }
        } else {
            return "该用户名已存在";
        }
    }
}
