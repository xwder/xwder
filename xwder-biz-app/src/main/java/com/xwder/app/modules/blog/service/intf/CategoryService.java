package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.sysmodules.blog.dto.CategoryDto;
import javafx.scene.chart.CategoryAxis;
import org.springframework.data.domain.Page;

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


    /**
     * 分页查询category
     *
     * @param categoryDto
     * @return
     */
    Page listCategoryPageData(CategoryDto categoryDto);

    /**
     * 分页查询category 每个分类下博文数量
     * @param categoryDto
     * @return
     */
    Page listCategoryArticleCountPageData(CategoryDto categoryDto);

    /**
     * 保存 category
     *
     * @param category
     * @return
     */
    Category saveCategory(Category category);

    /**
     * 修改 category
     *
     * @param category
     * @return
     */
    Category updateCategory(Category category);

    /**
     * 删除 category 根据id
     * @param id
     * @return
     */
    CommonResult deleteCategoryById(long id);

}
