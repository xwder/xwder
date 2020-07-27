package com.xwder.app.modules.blog.repository;

import cn.hutool.core.bean.BeanUtil;
import com.xwder.app.XwderApplication;
import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.utils.MapUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderApplication.class)
public class BlogRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    @PersistenceContext
    private EntityManager em;

    @Test
    public void findByUserIdTest() {
        Long userId = 1L;
        List<Category> categoryList = categoryRepository.findByUserId(userId);
        System.out.println(categoryList);

        List<Tag> tagList = tagRepository.findByUserIdAndAvailable(1L, 1);
        System.out.println(tagList);
    }

    @Test
    public void listArticleByUserIdTest() {
        List<Object> articles = articleRepository.listArticleByUserId3(1L);
        List<Map<String, Object>> maps = articleRepository.listArticleByUserId2(1L);

        String sql = "SELECT id,user_id,user_name,category_id,tags,title,summary,preview_image,favors,comments,read_count,article_type,is_avaliable,publish_time\n" +
                "from blog_article \n" +
                "WHERE status = 1 and is_avaliable = 1 and user_id =  1 " +
                "ORDER BY gmt_modified desc ";
        Query query = em.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> rows = query.getResultList();
        System.out.println(rows);

        for(Map map : rows){
            Map convertMap = MapUtil.convertMap(map);
            Article article = BeanUtil.mapToBean(convertMap,Article.class,true);
            System.out.println(article);
        }

        Pageable pageable = new PageRequest(0,3, Sort.Direction.DESC,"id");
        List<Map<String, Object>> maps1 = articleRepository.listArticleByUserId2(1L, pageable);
        System.out.println(maps1);


    }
}
