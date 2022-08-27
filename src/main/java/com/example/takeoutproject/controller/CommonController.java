package com.example.takeoutproject.controller;

import com.example.takeoutproject.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * file upload and download
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.img.path}")
    private String basePath;

    /**
     * file upload
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public JsonData upload(MultipartFile file) {
        // file is temporary, need to save to somewhere, otherwise file lost after request
        log.info(file.toString());

        String originalFilename = file.getOriginalFilename();

        // use uuid to generate file name in case overwrite
       String fileName = UUID.randomUUID().toString()
               + originalFilename.substring(originalFilename.lastIndexOf("."));

       File dir = new File(basePath);
       if (!dir.exists()) {
           dir.mkdirs();
       }

        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonData.buildSuccess(fileName);
    }

    /**
     * file download
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            // use input stream to read file content
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            // use output stream to write back to web and display
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) >= 0) {
                outputStream.write(bytes);
            }
            // close stream
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
