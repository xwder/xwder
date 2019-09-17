package com.xwder.cqust.controller;

import com.xwder.cqust.service.KyStudentService;
import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.framework.utils.page.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xwder
 * @Date: 2019/7/16 00:37
 * @Description:
 */
@RequestMapping("/ky/student")
@Controller
public class StudentController {

    @Autowired
    private KyStudentService studentService;

    @GetMapping("/page/{page}/{size}")
    @ResponseBody
    public Result<KyStudent> listByPage(@PathVariable Integer page, @PathVariable Integer size, String sortField, Sort.Direction order) {
        PageData pageInfo = studentService.listStudent(page, size, sortField, order);
        return ResultUtil.success(pageInfo);
    }

}
