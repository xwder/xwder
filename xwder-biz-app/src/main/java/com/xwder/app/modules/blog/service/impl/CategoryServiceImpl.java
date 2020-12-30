package com.xwder.app.modules.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xwder.app.advice.BizException;
import com.xwder.app.common.result.CommonResult;
import com.xwder.app.common.result.ResultCode;
import com.xwder.app.config.web.GlobalDataCacheConfig;
import com.xwder.app.consts.RedisConstants;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.helper.dao.DAOHelper;
import com.xwder.app.helper.dao.NativeSQL;
import com.xwder.app.modules.blog.dao.BlogDaoResourceHandler;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.repository.CategoryRepository;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.sysmodules.blog.dto.CategoryDto;
import com.xwder.app.utils.PageUtil;
import com.xwder.app.utils.RedisUtil;
import com.xwder.app.utils.SessionUtil;
import com.xwder.app.utils.UpdateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GlobalDataCacheConfig globalDataCacheConfig;

    /**
     * 根据id查询分类信息
     *
     * @param id 分类id
     * @return 分类信息
     */
    @Override
    public Category getCategoryById(Long id) {
        String categoryRedisKey = RedisConstants.BLOG_ARTICLE_CATEGORY + ":" + id;
        Category category = (Category) redisUtil.get(categoryRedisKey);
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
     * @param userId 用户id
     * @return 分类列表
     */
    @Override
    public List listCategoryByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }


    /**
     * https://blog.csdn.net/ctwy291314/article/details/105437682/
     * 根据用户id查询分类信息
     *
     * @param userId 用户id
     * @return 用户分类数量信息
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

        String querySql = DAOHelper.getSQL(BlogDaoResourceHandler.class, "query_category_count");
        List params = new ArrayList<>();
        params.add(userId);

        List<Map> rows = NativeSQL.findByNativeSQL(querySql, params);
//        List<Map> rows = categoryRepository.listCagetroyCount(userId);
        return rows;

    }

    /**
     * 分页查询category
     *
     * @param categoryDto 分类dto
     * @return 分类分页对象
     */
    @Override
    public Page listCategoryPageData(CategoryDto categoryDto) {
        return categoryRepository.findAll(categoryDto);
    }

    /**
     * 分页查询category 每个分类下博文数量
     *
     * @param categoryDto 分页dto
     * @return 分页对象
     */
    @Override
    @Transactional(readOnly = true)
    public Page listCategoryArticleCountPageData(CategoryDto categoryDto) {
        String querySql = DAOHelper.getSQL(BlogDaoResourceHandler.class, "query_category_aritcle_count");
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
        List params = new ArrayList<>();
        params.add(sessionUser.getId());
        params.add(sessionUser.getId());
        Pageable pageable = PageRequest.of(categoryDto.getPage(), categoryDto.getLimit(), null);
        List<Map> rows = NativeSQL.findByNativeSQLPageable(querySql, params, pageable);
        int count = NativeSQL.countByNativeSQL(querySql, params);
        Page page = PageUtil.page(rows, pageable, count);
        return page;
    }

    /**
     * 保存 category
     *
     * @param category 分类信息
     * @return 保存后的分类信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Category saveCategory(Category category) {
        Category save = categoryRepository.save(category);
        globalDataCacheConfig.initPortalData();
        return save;
    }

    /**
     * 修改 category
     *
     * @param category 分类信息
     * @return 更新后的分类信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Category updateCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(category.getId());
        Category existCategory = categoryOptional.orElse(new Category());
        UpdateUtil.copyNullProperties(category, existCategory);
        categoryRepository.save(existCategory);
        // 更新首页分类缓存
        globalDataCacheConfig.initPortalData();
        // 更新redis 分类缓存
        String categoryRedisKey = RedisConstants.BLOG_ARTICLE_CATEGORY + ":" + existCategory.getId();
        redisUtil.del(categoryRedisKey);

        return existCategory;
    }

    /**
     * 删除分类 如果该分类下存在博文 不允许删除
     *
     * @param id 分类id
     * @return 删除响应信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CommonResult deleteCategoryById(long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        Category existCategory = categoryOptional.orElseThrow(() -> new BizException(ResultCode.ERROR));
        int count = articleService.countByCategoryId(existCategory.getId());
        if (count > 0) {
            return CommonResult.failed("删除失败,该分类下存在博客文章不能删除");
        }
        categoryRepository.deleteById(id);
        // 更新redis 分类缓存
        String categoryRedisKey = RedisConstants.BLOG_ARTICLE_CATEGORY + ":" + existCategory.getId();
        redisUtil.del(categoryRedisKey);

        // 更新首页分类缓存
        globalDataCacheConfig.initPortalData();

        return CommonResult.success(ResultCode.SUCCESS, "删除成功");
    }
}
