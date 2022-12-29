package com.example.server.Controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class test {
    @ResponseBody
    //使用produces属性来指定返回文件的类型
    @RequestMapping(value = "/testApi", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] testApi() {
        FileInputStream inputStream = null;
        byte[] bytes = new byte[0];
        try {
            //创建文件对象
            File img = new File("D:/images/2233.png");
            //创建文件字节输入流，对文件数据以字节的形式进行读取操作如读取图片视频等
            inputStream = new FileInputStream(img);
            //创建一个长度跟图片大小相同的字节数组
            bytes = new byte[inputStream.available()];
            //把读取到的字节数据存入字节数组当中
            inputStream.read(bytes, 0, inputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回数据
        return bytes;
    }
}
