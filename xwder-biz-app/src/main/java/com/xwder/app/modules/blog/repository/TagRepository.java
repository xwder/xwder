package com.xwder.app.modules.blog.repository;

import com.xwder.app.modules.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据userId查询博客标签
     *
     * @param userId
     * @param available 是否可用 1-可用 0 不可用
     * @return
     */
    List<Tag> findByUserIdAndAvailable(Long userId, Integer available);

}
