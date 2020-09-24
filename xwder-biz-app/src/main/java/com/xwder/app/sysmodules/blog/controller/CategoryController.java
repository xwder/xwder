package com.xwder.app.sysmodules.blog.controller;

import com.xwder.app.advice.TaskException;
import com.xwder.app.common.result.CommonResult;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.sysmodules.blog.dto.CategoryDto;
import com.xwder.app.utils.SessionUtil;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author wande
 * @version 1.0
 * @date 2020/09/18
 */

@Controller
@RequestMapping("/sys/blog")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 博客分类列表页面
     *
     * @return
     */
    @RequestMapping(value = "/categoryListPage")
    public String listCategoryPage() {
        return "sys/blog/categoryList";
    }

    /**
     * 分页查询 category list
     *
     * @param categoryDto
     * @return
     */
    @RequestMapping("/categoryList")
    @ResponseBody
    public CommonResult listCategory(CategoryDto categoryDto) {
        Page  categoryPage = categoryService.listCategoryArticleCountPageData(categoryDto);
        return CommonResult.success(categoryPage);
    }

    /**
     * 新增 Category
     */
    @PostMapping("/addCategory")
    @ResponseBody
    public CommonResult addCategory(@Validated Category category) throws SchedulerException, TaskException {
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConstant.SESSION_USER);
        category.setAvailable(1);
        category.setUserId(sessionUser.getId());
        category.setUserName(sessionUser.getUserName());
        category.setGmtCreate(new Date());
        category.setGmtModified(new Date());
        Category saveCategory = categoryService.saveCategory(category);
        return CommonResult.success(saveCategory);
    }

    /**
     * 修改分类
     */
    @PostMapping("/updateCategory")
    @ResponseBody
    public CommonResult updateCategory(@Validated Category category) throws SchedulerException, TaskException {
        Category saveCategory = categoryService.updateCategory(category);
        return CommonResult.success(saveCategory);
    }

    /**
     * 删除 分类
     */
    @PostMapping("/deleteCategory")
    @ResponseBody
    public CommonResult deleteCategory(long id) throws SchedulerException, TaskException {
        CommonResult commonResult = categoryService.deleteCategoryById(id);
        return commonResult;
    }
}
