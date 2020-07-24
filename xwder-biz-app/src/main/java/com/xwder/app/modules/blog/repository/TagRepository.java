package com.xwder.app.modules.blog.repository;

import com.xwder.app.modules.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据userId查询博客标签
     *
     * @param userId
     * @param available 是否可用 1-可用 0 不可用
     * @return
     */
    List<Tag> findByUserIdAndAvailable(Long userId, Integer available);

    /**
     * 根据id查询tags
     *
     * @param ids
     * @return
     */

    @Query(value = "SELECT * FROM blog_tag WHERE id IN (:ids)",nativeQuery = true)
    List<Tag> listTagByUserId(@Param("ids") List<Long> ids);

}
