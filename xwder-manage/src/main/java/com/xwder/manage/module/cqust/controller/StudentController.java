package com.xwder.manage.module.cqust.controller;

import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.page.PageData;
import com.xwder.manage.framework.web.controller.BaseController;
import com.xwder.common.page.TableDataInfo;
import com.xwder.common.page.TableSupport;
import com.xwder.manage.module.cqust.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xwder
 * @Date: 2019/7/10 20:40
 * @Description:
 */

@RequestMapping("/ky/student")
@Controller
public class StudentController extends BaseController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/page")
    public String tag(Model model) {
        return "ky/student/student";
    }

    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(KyStudent kyStudent) {
        pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum() - 1;
        Integer pageSize = pageDomain.getPageSize();
        PageData page = studentService.listStudent(pageNum, pageSize, null, null);
        TableDataInfo rspData = new TableDataInfo();
        if (page == null) {
            rspData.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            rspData.setRows(null);
            rspData.setTotal(0);
            return rspData;
        }
        rspData.setCode(0);
        rspData.setRows(page.getData());
        rspData.setTotal(page.getTotal());
        return rspData;
    }
}
