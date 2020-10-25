package com.xwder.app.modules.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.modules.comment.entity.CommentInfo;
import com.xwder.app.modules.comment.entity.CommentReply;
import com.xwder.app.modules.comment.repository.CommentReplyRepository;
import com.xwder.app.modules.comment.service.intf.CommentInfoService;
import com.xwder.app.modules.comment.service.intf.CommentReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 评论回复服务service
 *
 * @author xwder
 * @date 2020年10月23日
 */
@Slf4j
@Service
public class CommentReplyServiceImpl implements CommentReplyService {

    @Autowired
    private CommentReplyRepository commentReplyRepository;

    @Autowired
    private CommentInfoService commentInfoService;

    @Autowired
    private ArticleService articleService;

    /**
     * 根据commentid 查询回复的评论信息
     *
     * @param commentIds
     * @return
     */
    @Override
    public List<CommentReply> listCommentReplyByCommentIds(List<Long> commentIds) {
        List<Map> maps = commentReplyRepository.listCommentReplyByCommentIds(commentIds);
        List<CommentReply> list = new ArrayList<>();
        for (Map map : maps) {
            CommentReply commentReply = BeanUtil.mapToBean(map, CommentReply.class, true);
            list.add(commentReply);
        }
        return list;
    }

    /**
     * 保存或者更新 回复消息
     *
     * @param commentReply
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateCommentReply(CommentReply commentReply) {
        commentReplyRepository.save(commentReply);
    }

    /**
     * 根据commentid查询评论主体，然后评论主体评论数加1
     *
     * @param commentId
     * @param commentCount
     */
    @Override
    public void updateCommentCountByCommentId(Long commentId, Integer commentCount) {
        CommentInfo commentInfo = commentInfoService.getCommentInfoById(commentId);
        if (commentInfo == null) {
            log.error("查询评论{}错误没有查到",commentId);
            return;
        }
        articleService.addArticleCommentCount(commentInfo.getSubjectId(),commentCount);
    }
}
