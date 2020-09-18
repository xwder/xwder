package com.xwder.app.modules.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xwder.app.consts.RedisConstant;
import com.xwder.app.helper.dao.DAOHelper;
import com.xwder.app.helper.dao.NativeSQL;
import com.xwder.app.modules.blog.dao.CagetoryDaoResourceHandler;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.repository.CategoryRepository;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.sysmodules.blog.dto.CategoryDto;
import com.xwder.app.utils.RedisUtil;
import com.xwder.app.utils.UpdateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @Transactional(readOnly = true)
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

        String querySql = DAOHelper.getSQL(CagetoryDaoResourceHandler.class, "query_category_count");
        List params = new ArrayList<>();
        params.add(userId);

        List<Map> rows = NativeSQL.findByNativeSQL(querySql, params, null);
        return rows;
    }

    /**
     * 分页查询category
     *
     * @param categoryDto
     * @return
     */
    @Override
    public Page listCategoryPageData(CategoryDto categoryDto) {
        return categoryRepository.findAll(categoryDto);
    }

    /**
     * 保存 category
     *
     * @param category
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * 修改 category
     *
     * @param category
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Category updateCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(category.getId());
        Category existCategory = categoryOptional.get();
        UpdateUtil.copyNullProperties(category, existCategory);
        categoryRepository.save(existCategory);
        return existCategory;
    }
}
