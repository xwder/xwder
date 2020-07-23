package com.xwder.app.modules.blog.service.impl;

import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.repository.CategoryRepository;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author xwder
 * @Description: 分类service
 * @date 2020/7/23 22:29
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 根据id查询分类信息
     *
     * @param id
     * @return
     */
    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.get();
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
}
