package com.example.server.Controller;

import com.example.server.returnObject.userInformation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class me {
    @ResponseBody
    @RequestMapping(value = "/meApi", method = RequestMethod.POST)
    public userInformation meApi(HttpServletRequest request) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        //参数设置为false的话就不会创建新的，而是返回null
        HttpSession Session = request.getSession(false);
        if (Session != null) {
            //获取数据
            String username = (String) Session.getAttribute("username");
            int id = (int) Session.getAttribute("id");
            return new userInformation(username, id);
        } else {
            return null;
        }
    }
}
