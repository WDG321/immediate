package com.example.server.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*前端控制器*/
@Controller
public class index {
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    //注意：在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。
    //@ResponseBody
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }
}
