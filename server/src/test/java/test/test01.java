package test;

import org.junit.jupiter.api.Test;

import java.io.File;

public class test01 {
    @Test
    public void test() {
        File img = new File("D:/2233.png");
        System.out.println(img);
        System.out.println(img.length());//获取文件的字节数大小
    }
}
