package com.xwder.cqust.controller;

import com.xwder.cqust.domain.KyStudent;
import com.xwder.cqust.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/10 20:40
 * @Description:
 */

@RequestMapping("/ky/student")
@Controller
public class StudentController{

    @Autowired
    private StudentService studentService;


    @GetMapping("/list")
    @ResponseBody
    public Object list(KyStudent kyStudent) {
        List<KyStudent> kyStudentList = studentService.listKyStudent();
        return kyStudentList;
    }

    @GetMapping("/page/{page}/{size}")
    @ResponseBody
    public Object listByPage(@PathVariable Integer page, @PathVariable Integer size ) {
        List<KyStudent> kyStudentList = studentService.findAll(page,size);
        return kyStudentList;
    }
}
