package com.xwder.app.modules.blog.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.modules.blog.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseJpaRepository<Category, Long> {

    /**
     * 根据userId查询博客分类
     *
     * @param userId
     * @return
     */
    @Query(value = "select * from blog_category t where t.user_id = ?1 and is_available = 1 order by sequence desc", nativeQuery = true)
    List<Category> findByUserId(Long userId);

    /**
     *
     * @param id
     */
    @Override
    void deleteById(Long id);
}
