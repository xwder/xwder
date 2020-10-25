package com.xwder.app.modules.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.xwder.app.helper.dao.NativeSQL;
import com.xwder.app.modules.comment.entity.CommentInfo;
import com.xwder.app.modules.comment.entity.CommentReply;
import com.xwder.app.modules.comment.repository.CommentInfoRepository;
import com.xwder.app.modules.comment.service.intf.CommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @Transactional(readOnly = true)
    public List<CommentInfo> listCommentInfoByTypeAndSubjectId(Integer type, Long subjectId) {
        List<CommentInfo> commentInfos = new ArrayList<>();
        List<Map<String, Object>> maps = commentInfoRepository.listCommentInfoByTypeAndSubjectId(type, subjectId);
        for (Map<String, Object> map : maps) {
            CommentInfo commentInfo = BeanUtil.mapToBean(map, CommentInfo.class, true);
            commentInfos.add(commentInfo);
        }
        return commentInfos;
    }

    /**
     * 根据id查询commentInfo
     *
     * @param commentId
     * @return
     */
    @Override
    public CommentInfo getCommentInfoById(long commentId) {
        Optional<CommentInfo> optionalCommentInfo = commentInfoRepository.findById(commentId);
        return optionalCommentInfo.get();
    }
}
