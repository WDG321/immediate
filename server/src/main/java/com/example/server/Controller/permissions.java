package com.example.server.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*处理通行权限*/
@Controller
public class permissions {
    //通过session是否过期来判断是否有权限
    //@CrossOrigin用于设置允许跨域,但无法解决cookie问题
    //@CrossOrigin()
    @ResponseBody
    @RequestMapping(value = "/permissionsApi", method = RequestMethod.POST)
    public boolean permissionsApi(HttpServletRequest request) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        //参数设置为false的话就不会创建新的，而是返回null
        HttpSession Session = request.getSession(false);
        //判断是否存在Session,不存在就代表过期了
        if (Session == null) {
            return false;
        } else {
            return true;
        }
    }
}
