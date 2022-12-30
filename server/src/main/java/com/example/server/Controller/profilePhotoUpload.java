package com.example.server.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/*处理头像上传*/
@Controller
public class profilePhotoUpload {
    /**
     * 时间格式化
     */
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
    /**
     * 图片保存路径，自动从yml文件中获取数据
     * 示例： D:/images/
     */
    @Value("${file-save-path}")
    private String fileSavePath;

    @ResponseBody
    @RequestMapping(value = "/profilePhotoUploadApi")
    public String profilePhotoUploadApi(MultipartFile profilePhoto, HttpServletRequest request) {
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        //参数设置为false的话就不会创建新的，而是返回null
        HttpSession Session = request.getSession(false);
        //判断是否登录或者是否登录过期
        if (Session == null) {
            //未登录就不进行操作了
            return "请登录";
        }
        //没有数据则返回提示
        if (profilePhoto == null) {
            return "请上传头像";
        }
        //1.后半段目录：  2020/03/15
        String directory = simpleDateFormat.format(new Date());
        /*
         *  2.文件保存目录  D:/images/2020/03/15/
         *  如果目录不存在，则创建
         */
        File dir = new File(fileSavePath + directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        System.out.println("图片上传，保存位置：" + fileSavePath + directory);
        //3.给文件重新设置一个名字
        //suffix为获取上传文件的后缀
        String suffix = profilePhoto.getOriginalFilename().substring(profilePhoto.getOriginalFilename().lastIndexOf("."));
        //使用uuid防止文件名重复
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        //4.创建这个新文件
        File newFile = new File(fileSavePath + directory + newFileName);
        //5.复制操作
        try {
            //.transferTo()是springmvc封装的方法，用于图片上传时，把内存中图片写入磁盘
            profilePhoto.transferTo(newFile);
            //协议 :// ip地址 ：端口号 / 文件目录(/images/2020/03/15/xxx.jpg)
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/" + directory + newFileName;
            System.out.println("图片上传，访问URL：" + url);
            return "图片上传成功,访问URL:" + url;
        } catch (IOException e) {
            return "IO异常！";
        }
    }
}
