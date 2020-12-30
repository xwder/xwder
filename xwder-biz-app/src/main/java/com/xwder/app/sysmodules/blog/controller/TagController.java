package com.xwder.app.sysmodules.blog.controller;

import com.xwder.app.advice.TaskException;
import com.xwder.app.common.result.CommonResult;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.modules.blog.service.intf.TagService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.sysmodules.blog.dto.TagDto;
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
 * @date 2020/09/21
 */
@Controller
@RequestMapping("/sys/blog")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 博客 tag 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/tagListPage")
    public String listTagPage() {
        return "sys/blog/tagList";
    }

    /**
     * 分页查询 tag list
     *
     * @param tagDto
     * @return
     */
    @RequestMapping("/tagTist")
    @ResponseBody
    public CommonResult listTag(TagDto tagDto) {
        Page tagPage = tagService.listTagArticleCountPageData(tagDto);
        return CommonResult.success(tagPage);
    }

    /**
     * 新增 tag
     */
    @PostMapping("/addTag")
    @ResponseBody
    public CommonResult addTag(@Validated Tag tag) throws SchedulerException, TaskException {
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
        tag.setUserId(sessionUser.getId());
        tag.setUserName(sessionUser.getUserName());
        tag.setAvailable(1);
        tag.setGmtCreate(new Date());
        tag.setGmtModified(new Date());
        Tag saveTag = tagService.saveTag(tag);
        return CommonResult.success(saveTag);
    }

    /**
     * 修改 tag
     */
    @PostMapping("/updateTag")
    @ResponseBody
    public CommonResult updateTag(@Validated Tag tag) throws SchedulerException, TaskException {
        Tag updateTag = tagService.updateTag(tag);
        return CommonResult.success(updateTag);
    }

    /**
     * 删除 tag
     */
    @PostMapping("/deleteTag")
    @ResponseBody
    public CommonResult deleteTag(long id) throws SchedulerException, TaskException {
        CommonResult commonResult = tagService.deleteTagById(id);
        return commonResult;
    }
}

