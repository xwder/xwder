package com.xwder.manage.module.file.controller;

import com.xwder.manage.module.file.config.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @className: FileController
 * @description:
 * @author: xwder
 * @date: 2019-8-3 01:04:51
 * @version: 1.0
 */
@Controller
public class FileController {
    @Autowired
    QiNiuUtils qiNiuUtils;

    @GetMapping("/file/img")
    public String img(Model model) {
        model.addAttribute("list", qiNiuUtils.selectImgList());
        return "file/img/img";
    }
}