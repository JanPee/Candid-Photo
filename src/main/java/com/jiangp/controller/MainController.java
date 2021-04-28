package com.jiangp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Controller
public class MainController {

    private static String imagePath = "C:\\Users\\jiang\\Desktop\\";
//    private static String imagePath = "/root/img/";


    @RequestMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }

    /**
     * base64字符串上传
     * @param photo
     */
    @RequestMapping(value = "/base64",method = RequestMethod.GET)
    @ResponseBody
    public void base64(String photo){
        GenerateImage(photo);
    }


    //base64转图片并储存
    public static String GenerateImage(String base64) {
        if (base64 == null) {
            return "生成文件失败，请给出相应的数据。";
        }
        try {
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH：mm：ss");
            Files.write(Paths.get(imagePath+ df.format(new Date())+".jpg"), Base64.getMimeDecoder().decode(base64), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "指定路径下生成文件成功！";
    }
}
