package com.example.server.Controller;

import com.example.server.entity.user;
import com.example.server.mappers.userMapper;
import com.example.server.util.ObjectMapperUtil;
import com.example.server.util.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/*处理添加好友(未完成)*/
@Controller
public class addContact {
    @ResponseBody
    @RequestMapping(value = "/addContactApi", method = RequestMethod.POST)
    public int addContactApi() {
        //通过工具类获取sqlSession对象
        org.apache.ibatis.session.SqlSession sqlSession = SqlSession.getSqlSession();
        //通过代理模式创建UserMapper接口的代理实现类对象
        userMapper mapper = sqlSession.getMapper(userMapper.class);
        //查询要添加用户
        user userObj = mapper.queryUser("张三");
        //密码和联系人列表不能保存进去,所以设置为null
        userObj.setPassword(null);
        userObj.setContact(null);
        //获取当前用户的联系人列表信息,并转为list
        String contact = "[]";//注册时记得把联系人列表信息设置为[],不然以后会出bug
        ArrayList contactObj = ObjectMapperUtil.toObj(contact, ArrayList.class);
        //向list中添加数据
        contactObj.add(userObj);
        //把list转为json
        String contactJson = ObjectMapperUtil.toJSON(contactObj);
        System.out.println(contactJson);
        //把查询到的用户添加到联系人
        return mapper.addContact("李四", contactJson);
    }
}
