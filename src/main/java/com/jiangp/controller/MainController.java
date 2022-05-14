package com.jiangp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Controller
@Slf4j
public class MainController {

    @Value("${image.url}")
    private String imagePath;
    @Value("${image.suffix}")
    private String suffix;

    private Path getPath(){
        String path = imagePath + System.currentTimeMillis() + suffix;
        log.info("========>>>>>>>>  存储文件路径：{}", path);
        return Paths.get(path);
    }

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    /**
     * base64字符串上传
     *
     * @param photo
     */
    @RequestMapping(value = "/base64", method = RequestMethod.GET)
    @ResponseBody
    public void base64(String photo) throws Exception {
        if (photo == null) {
            log.error("生成文件失败，请给出相应的数据。");
            return;
        }
        byte[] decode = Base64.getMimeDecoder().decode(photo);
        Files.write(getPath(), decode, StandardOpenOption.CREATE);

        log.info("指定路径下生成文件成功。");
    }

    /**
     * MultipartFile上传
     * @param photo
     */
    @ResponseBody
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public void upload(MultipartFile photo) throws Exception {
        if(photo.isEmpty()){
            log.error("生成文件失败，请给出相应的数据。");
            return;
        }
        Files.write(getPath(), photo.getBytes(), StandardOpenOption.CREATE);
        log.info("指定路径下生成文件成功。");
    }
}
