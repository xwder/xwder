package com.xwder.app.modules.comment.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.modules.comment.entity.CommentInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentInfoRepository extends BaseJpaRepository<CommentInfo, Long> {

    /**
     * 根据type和subjectId查询评论列表
     *
     * @param type      评论内容分类
     * @param subjectId 评论内容id
     * @return
     */
    @Query(value = "SELECT " +
            "	t1.*, " +
            "	u.avatar AS 'from_avatar', " +
            "	u.user_name AS 'from_name'  " +
            "FROM " +
            "	( SELECT * FROM comments_info t WHERE t.type =  ?1  AND subject_id =  ?2 AND t.available = 1 ) t1\n" +
            "	LEFT JOIN app_user u ON t1.from_id = u.id", nativeQuery = true)
    List<CommentInfo> listCommentInfoByTypeAndSubjectId(Integer type, Long subjectId);
}
