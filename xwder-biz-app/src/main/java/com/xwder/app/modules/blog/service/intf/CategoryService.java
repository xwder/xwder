package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.modules.blog.entity.Category;

import java.util.List;
import java.util.Map;

/**
 * 文章分类service
 */
public interface CategoryService {

    /**
     * 根据id查询分类信息
     *
     * @param id
     * @return
     */
    Category getCategoryById(Long id);

    /**
     * 根据用户id查询分类信息
     *
     * @param userId
     * @return
     */
    List listCategoryByUserId(Long userId);


    /**
     * 根据用户id查询分类信息、每个分类文章数量
     *
     * @param userId
     * @return
     */
    List<Map> listCategoryArticleCount(Long userId);

}
