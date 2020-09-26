package com.sherwin.ebook.controller;

import com.sherwin.ebook.utilities.UploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @GetMapping("/admin/product/upload")
    public String uploadHome(Model model){

        model.addAttribute("filename","");
        return "admin/product/file";
    }

    @PostMapping("/admin/fileUpload")
    public String upload(@RequestParam(value = "file") MultipartFile file, Model model ) {

        String filename = file.getOriginalFilename();

        // 存放上传图片的文件夹
        File fileDir = UploadUtils.getImgDirFile();
        // 输出文件夹绝对路径  -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径
        System.out.println(fileDir.getAbsolutePath());

        try {
            // 构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
            System.out.println(newFile.getAbsolutePath());

            // 上传图片到 -》 “绝对路径”
            file.transferTo(newFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        filename = "/images/" + filename;
        System.out.println(filename);
        model.addAttribute("filename", filename);

        return "redirect:/admin/product/add";
    }

}
