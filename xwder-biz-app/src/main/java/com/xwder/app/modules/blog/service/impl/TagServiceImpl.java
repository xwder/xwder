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


    @Override
    public List<Tag> listTagById(Long ids) {
        return null;
    }
}
