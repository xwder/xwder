package com.xwder.app.modules.blog.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleRepository extends BaseJpaRepository<Article, Long> {

    /**
     * 根据用户分页查询article
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndStatusAndAvailable(Long userId, Integer status, Integer available, Pageable pageable);

    /**
     * 根据用户、分类 分页查询article
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndCategoryIdAndStatusAndAvailable(Long userId, Long categoryId, Integer status, Integer available, Pageable pageable);


    /**
     * 根据用户id查询文章列表
     *
     * @param userId
     * @return
     */
    @Query(value = "SELECT id,user_id,user_name,category_id,tags,title,summary,preview_image,favors,comments,read_count,article_type,publish_time\n" +
            "from blog_article \n" +
            "WHERE status = 1 and is_available = 1 and user_id =  ?1 " +
            "ORDER BY gmt_modified desc ", nativeQuery = true)
    List<Object> listArticleByUserId3(Long userId);

    /**
     * 根据用户id查询文章列表
     *
     * @param userId
     * @return
     */
    @Query(value = "SELECT new map(id,userId,userName,categoryId,tags,title,summary,previewImage,favors,comments,readCount,articleType,publishTime) from  Article " +
            "WHERE status = 1 and available = 1 and userId =  :userId " +
            "ORDER BY gmtModified desc ")
    List<Map<String, Object>> listArticleByUserId2(@Param("userId") Long userId);

    /**
     * 根据用户id查询文章列表
     *
     * @param userId
     * @return
     */
    @Query(value = "SELECT new map(id,userId,userName,categoryId,tags,title,summary,previewImage,favors,comments,readCount,articleType,publishTime) from  Article " +
            "WHERE status = 1 and available = 1 and userId =  :userId " +
            "ORDER BY gmtModified desc ")
    List<Map<String, Object>> listArticleByUserId2(@Param("userId") Long userId, Pageable pageable);


    /**
     * 修改阅读数
     *
     * @param id
     * @param readCount
     */
    @Modifying
    @Query(value = "update blog_article t set t.read_count = ?2 where t.id = ?1", nativeQuery = true)
    void updateArticleReadCountById(Long id, Long readCount);

    /**
     * 根据分类id查询文章数量
     *
     * @param categoryId
     * @returnC
     */
    int countByCategoryId(Long categoryId);

    /**
     * 根据id查询 文章
     *
     * @param ids
     * @return
     */
    @Query(value = "SELECT * FROM blog_article WHERE id IN (:ids)", nativeQuery = true)
    List<Article> listArticleByIds(@Param("ids") List<Long> ids);
}
