package com.xwder.app.modules.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xwder.app.consts.RedisConstant;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.repository.CategoryRepository;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * @author xwder
 * @Description: 分类service
 * @date 2020/7/23 22:29
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据id查询分类信息
     *
     * @param id
     * @return
     */
    @Override
    public Category getCategoryById(Long id) {
        Category category = null;
        String categoryRedisKey = RedisConstant.BLOG_ARTICLE_CATEGORY + ":" + id;
        category = (Category) redisUtil.get(categoryRedisKey);
        if (category == null) {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (categoryOptional.isPresent()) {
                redisUtil.set(categoryRedisKey, categoryOptional.get(), -1);
                return categoryOptional.get();
            }
        }
        return category;
    }

    /**
     * 根据用户id查询分类信息
     *
     * @param userId
     * @return
     */
    @Override
    public List listCategoryByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }


    /**
     * https://blog.csdn.net/ctwy291314/article/details/105437682/
     * 根据用户id查询分类信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map> listCategoryArticleCount(Long userId) {
        if (userId == null) {
            List<User> users = userService.listManagerUser();
            if (CollectionUtil.isEmpty(users)) {
                HashMap<Object, Object> map = Maps.newHashMap();
                ArrayList arrayList = Lists.newArrayList();
                arrayList.add(map);
                return arrayList;
            }
            userId = users.get(0).getId();
        }
        String sql = "SELECT t.id, t.user_id,t.category_name,IFNULL(t1.count,0) count\n" +
                "from (SELECT * from blog_category WHERE user_id = 1 and  is_avaliable = 1 ) t\n" +
                "LEFT JOIN (SELECT category_id,count(category_id) count from blog_article  WHERE  user_id = ? and   status = 1 and is_avaliable = 1 GROUP BY category_id) t1 \n" +
                "on t.id = t1.category_id ORDER BY t1.count DESC";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> rows = query.getResultList();
        return rows;
    }
}
