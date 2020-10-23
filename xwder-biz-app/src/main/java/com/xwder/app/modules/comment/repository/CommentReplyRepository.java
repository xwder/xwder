package com.xwder.app.modules.comment.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.modules.comment.entity.CommentReply;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends BaseJpaRepository<CommentReply, Long> {
}