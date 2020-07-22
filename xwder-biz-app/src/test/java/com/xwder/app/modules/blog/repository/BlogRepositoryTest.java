package com.xwder.app.modules.blog.repository;

import com.xwder.app.XwderApplication;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.entity.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @Test
    public void findByUserIdTest() {
        Long userId = 1L;
        List<Category> categoryList = categoryRepository.findByUserId(userId);
        System.out.println(categoryList);

        List<Tag> tagList = tagRepository.findByUserIdAndAvailable(1L, 1);
        System.out.println(tagList);
    }
}
