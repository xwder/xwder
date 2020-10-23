package com.xwder.app.modules.comment.service.impl;

import com.xwder.app.modules.comment.entity.CommentInfo;
import com.xwder.app.modules.comment.entity.CommentReply;
import com.xwder.app.modules.comment.repository.CommentInfoRepository;
import com.xwder.app.modules.comment.service.intf.CommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论服务service
 *
 * @author xwder
 * @date 2020年10月23日
 */
@Service
public class CommentInfoServiceImpl implements CommentInfoService {

    @Autowired
    private CommentInfoRepository commentInfoRepository;

    /**
     * 新增或者保存评论信息
     *
     * @param commentInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateCommentInfo(CommentInfo commentInfo) {
        commentInfoRepository.save(commentInfo);
    }

    /**
     * 根据type和subjectId查询评论列表
     *
     * @param type      评论内容分类
     * @param subjectId 评论内容id
     * @return
     */
    @Override
    public List<CommentInfo> listCommentInfoByTypeAndSubjectId(Integer type, Long subjectId) {
        List<CommentInfo> commentInfos = commentInfoRepository.listCommentInfoByTypeAndSubjectId(type, subjectId);
        return commentInfos;
    }
}
