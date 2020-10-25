package com.xwder.app.modules.comment.service.intf;

import com.xwder.app.modules.comment.entity.CommentInfo;

import java.util.List;

/**
 * 评论服务service
 *
 * @author xwder
 * @date 2020年10月23日
 */
public interface CommentInfoService {

    /**
     * 新增或者保存评论信息
     *
     * @param commentInfo
     */
    void saveOrUpdateCommentInfo(CommentInfo commentInfo);

    /**
     * 根据type和subjectId查询评论列表
     *
     * @param type      评论内容分类
     * @param subjectId 评论内容id
     * @return
     */
    List<CommentInfo> listCommentInfoByTypeAndSubjectId(Integer type, Long subjectId);

    /**
     * 根据id查询commentInfo
     *
     * @param commentId
     * @return
     */
    CommentInfo getCommentInfoById(long commentId);
}
