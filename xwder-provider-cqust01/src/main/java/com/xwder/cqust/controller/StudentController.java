package com.xwder.cqust.controller;

import com.xwder.cqust.service.StudentService;
import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.framework.utils.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xwder
 * @Date: 2019/7/10 20:40
 * @Description:
 */

@RequestMapping("/ky/student")
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/page/{page}/{size}")
    @ResponseBody
    public Result<KyStudent> listByPage(@PathVariable Integer page, @PathVariable Integer size, String sortField, Sort.Direction order) {
        PageInfo pageInfo = studentService.findAll(page, size, sortField, order);
        return ResultUtil.success(pageInfo);
    }
}
