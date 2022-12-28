package com.example.server.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class permissions {
    //通过session是否过期来判断是否有权限
    //@CrossOrigin用于设置允许跨域
    @CrossOrigin()
    @ResponseBody
    @RequestMapping(value = "/permissionsApi", method = RequestMethod.POST)
    public boolean permissionsApi(HttpServletRequest request) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        HttpSession session = request.getSession();
        //获取数据
        Object name = session.getAttribute("name");
        System.out.println(name);
        //此数据只有登录时才会添加进session，因此存在则表示未过期
        if (name != null) {
            return true;
        } else {
            //杀死此api创建的session，以免浪费内存
            session.invalidate();
            return false;
        }
    }
}
