package com.xwder.app.modules.blog.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.modules.blog.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xwder
 */
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

    /**
     * 根据userId查询博客分类
     *
     * @param userId
     * @return
     */
    @Query(value = "SELECT\n" +
            "			t.id,\n" +
            "			t.user_id,\n" +
            "			t.category_name,\n" +
            "			IFNULL( t1.count, 0 ) count\n" +
            " FROM\n" +
            "			( SELECT * FROM blog_category WHERE user_id = ?1 AND is_available = 1 ) t\n" +
            "			LEFT JOIN\n" +
            "			( SELECT category_id, count( category_id ) count FROM blog_article WHERE user_id = 1 AND STATUS = 1 AND is_available = 1 GROUP BY category_id ) t1  ON t.id = t1.category_id\n" +
            " ORDER BY\n" +
            "			t1.count DESC", nativeQuery = true)
    List<Map> listCagetroyCount(Long userId);
}
