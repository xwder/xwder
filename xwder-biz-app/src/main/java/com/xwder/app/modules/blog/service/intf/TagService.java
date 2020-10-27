package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.sysmodules.blog.dto.CategoryDto;
import com.xwder.app.sysmodules.blog.dto.TagDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 标签service
 */
public interface TagService {

    /**
     * 查询tags
     *
     * @param ids
     * @return
     */
    List<Tag> listTagById(List<Long> ids);


    /**
     * 根据userId查询
     *
     * @param userId
     * @return
     */
    List<Tag> listTagByUserId(Long userId);

    /**
     * 分页查询 tag 每个分类下博文数量
     *
     * @param tagDto
     * @return
     */
    Page listTagArticleCountPageData(TagDto tagDto);

    /**
     * 保存tag
     *
     * @param tag
     * @return
     */
    Tag saveTag(Tag tag);

    /**
     * 更新tag
     *
     * @param tag
     * @return
     */
    Tag updateTag(Tag tag);

    /**
     * 根据id删除标签 如果该标签存在文章 则不能删除
     *
     * @param id
     * @return
     */
    CommonResult deleteTagById(Long id);

    /**
     * 根据id查询tag
     *
     * @param id
     * @return
     */
    Tag findTagById(Long id);
}
