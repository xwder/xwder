package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.modules.blog.entity.Tag;

import java.util.List;

/**
 * 标签service
 */
public interface TagService {

    /**
     * 查询tags
     * @param ids
     * @return
     */
    List<Tag> listTagById(List<Long> ids);


    /**
     * 根据userId查询
     * @param userId
     * @return
     */
    List<Tag> listTagByUserId(Long userId);

}
