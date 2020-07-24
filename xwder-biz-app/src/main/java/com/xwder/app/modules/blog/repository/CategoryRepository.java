package com.xwder.app.modules.blog.repository;

import com.xwder.app.modules.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 根据userId查询博客分类
     *
     * @param userId
     * @return
     */
    @Query(value = "select * from blog_category t where t.user_id = ?1 and is_avaliable = 1", nativeQuery = true)
    List<Category> findByUserId(Long userId);

}
