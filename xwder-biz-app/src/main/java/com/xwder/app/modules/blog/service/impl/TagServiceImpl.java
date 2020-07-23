package com.xwder.app.modules.blog.service.impl;

import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.modules.blog.repository.TagRepository;
import com.xwder.app.modules.blog.service.intf.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/23
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    /**
     * 查询tags
     *
     * @param ids
     * @return
     */
    @Override
    public List<Tag> listTagById(List<Long> ids) {
        return tagRepository.listTagByUserId(ids);
    }

    /**
     * 根据userId查询
     *
     * @param userId
     * @return
     */
    @Override
    public List<Tag> listTagByUserId(Long userId) {
        return tagRepository.findByUserIdAndAvailable(userId, 1);
    }
}
