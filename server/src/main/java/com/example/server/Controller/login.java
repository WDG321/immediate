package com.example.server.Controller;

import com.example.server.mappers.user;
import com.example.server.mappers.userMapper;
import com.example.server.sqlSession.getSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class login {
    //@CrossOrigin用于设置允许跨域
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/loginApi", method = RequestMethod.POST)
    public boolean loginApi(String username, String password) {
        //通过工具类获取sqlSession对象
        SqlSession sqlSession = getSqlSession.getSqlSession();
        //通过代理模式创建UserMapper接口的代理实现类对象
        userMapper mapper = sqlSession.getMapper(userMapper.class);
        //通过mybatis查询数据库
        user user = mapper.login(username, password);
        if (user == null) {
            return false;
        } else {
            return true;
        }

    }
}
