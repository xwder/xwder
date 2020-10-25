package com.xwder.app.modules.comment.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.modules.comment.entity.CommentReply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentReplyRepository extends BaseJpaRepository<CommentReply, Long> {

    /**
     * 根据commentid 查询回复的评论信息
     *
     * @param commentIds
     * @return
     */
    @Query(value = "SELECT t.*, u2.user_name as to_name,u2.avatar as to_avatar\n" +
            "from(\n" +
            "SELECT u.user_name as from_name,u.avatar as from_avatar, t.*\n" +
            "from \n" +
            "(SELECT * from comments_reply t WHERE t.comment_id in (:commentId) and t.available = 1) t\n" +
            "LEFT JOIN app_user u on t.from_id = u.id\n" +
            ") t\n" +
            "LEFT JOIN  app_user u2 on t.to_id = u2.id", nativeQuery = true)
    List<Map> listCommentReplyByCommentIds(@Param("commentId") List<Long> commentIds);

}