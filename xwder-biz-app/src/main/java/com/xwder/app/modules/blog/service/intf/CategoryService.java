package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.modules.blog.entity.Category;

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

}
