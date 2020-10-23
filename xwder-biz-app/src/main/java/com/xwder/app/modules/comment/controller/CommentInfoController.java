package com.xwder.app.modules.comment.controller;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.modules.comment.dto.CommentInfoDto;
import com.xwder.app.modules.comment.entity.CommentInfo;
import com.xwder.app.modules.comment.service.intf.CommentInfoService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.utils.BeanUtils;
import com.xwder.app.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping(value = {"/comment"})
public class CommentInfoController {

    @Autowired
    private CommentInfoService commentInfoService;

    @Autowired
    private ArticleService articleService;


    @ResponseBody
    @RequestMapping(value = {"/blog/commit"})
    public CommonResult commitComment(@Validated CommentInfoDto commentInfoDto, HttpServletRequest request) {
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConstant.SESSION_USER);
        if (sessionUser == null) {
            return CommonResult.failed("请登录后发表评论");
        }
        CommentInfo commentInfo = new CommentInfo();
        BeanUtils.copyBeanProp(commentInfo, commentInfoDto);
        // 设置评论类型
        commentInfo.setType(SysConstant.COMMENT_TYPE_BLOG);
        commentInfo.setFromId(sessionUser.getId());
        commentInfo.setCommentTime(new Date());
        commentInfo.setLikeNum(0L);
        commentInfo.setAvailable(1);
        commentInfoService.saveOrUpdateCommentInfo(commentInfo);

        // 设置头像用户名
        commentInfo.setFromAvatar(sessionUser.getAvatar());
        commentInfo.setFromName(sessionUser.getUserName());

        // 修改文章的评论数
        articleService.addArticleCommentCount(commentInfo.getSubjectId(), 1);
        return CommonResult.success(commentInfo);
    }

}
