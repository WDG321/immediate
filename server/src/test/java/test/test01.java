package test;

import com.example.server.entity.user;
import com.example.server.mappers.userMapper;
import com.example.server.util.ObjectMapperUtil;
import com.example.server.util.SqlSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class test01 {
    @Test
    public void test() {
        File img = new File("D:/2233.png");
        System.out.println(img);
        System.out.println(img.length());//获取文件的字节数大小
    }

    @Test
    public void test02() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        user a = new user();
        //把对象转为json
        String a1 = objectMapper.writeValueAsString(a);
        System.out.println(a);
        System.out.println(a1);
        //把json转为对象
        user a2 = objectMapper.readValue(a1, user.class);
        System.out.println(a2);
        /*使用工具类*/
        System.out.println("**********");
        //把对象转为json
        String s = ObjectMapperUtil.toJSON(a);
        System.out.println(s);
        //把json转为对象
        user user = ObjectMapperUtil.toObj(s, user.class);
        System.out.println(user);
        System.out.println("**********");
        //
        String ss = "[1,2,3,4,5]";
    }
}
