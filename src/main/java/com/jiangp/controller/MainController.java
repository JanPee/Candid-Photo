package com.jiangp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

/**
 * 测试接口
 * @author jiangp
 * @date 2021/4/28
 */
@Controller
@Slf4j
public class MainController {

    /**
     * 文件存储前缀地址
     */
    @Value("${image.url}")
    private String imagePath;
    /**
     * 图片存储后缀
     */
    @Value("${image.suffix}")
    private String suffix;

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }


    /**
     * base64字符串上传
     * @param photo
     */
    @ResponseBody
    @GetMapping(value = "/base64")
    public void base64(String photo) throws IOException {
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
    public void upload(MultipartFile photo) throws IOException {
        if(photo.isEmpty()){
            log.error("生成文件失败，请给出相应的数据。");
            return;
        }
        Files.write(getPath(), photo.getBytes(), StandardOpenOption.CREATE);
        log.info("指定路径下生成文件成功。");
    }

    /**
     * 获取完整存储路径+文件名称
     */
    private Path getPath(){
        String path = imagePath + System.currentTimeMillis() + suffix;
        log.info("========>>>>>>>>  存储文件路径：{}", path);
        return Paths.get(path);
    }
}
