package com.xwder.app.modules.comment.service.intf;

import com.xwder.app.modules.comment.entity.CommentReply;

import java.util.List;

/**
 * 评论回复服务service
 *
 * @author xwder
 * @date 2020年10月23日
 */
public interface CommentReplyService {

    /**
     * 根据commentid 查询回复的评论信息
     *
     * @param commentIds
     * @return
     */
    List<CommentReply> listCommentReplyByCommentIds(List<Long> commentIds);

    /**
     * 保存或者更新 回复消息
     *
     * @param commentReply
     */
    void saveOrUpdateCommentReply(CommentReply commentReply);

    /**
     * 根据commentid查询评论主体，然后评论主体评论数加1
     *
     * @param commentId
     * @param commentCount
     */
    void updateCommentCountByCommentId(Long commentId,Integer commentCount);
}
