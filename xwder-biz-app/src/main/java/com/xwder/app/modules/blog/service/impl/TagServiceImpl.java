package com.xwder.app.modules.blog.service.impl;

import com.google.common.collect.Lists;
import com.xwder.app.advice.BizException;
import com.xwder.app.common.result.CommonResult;
import com.xwder.app.common.result.ResultCode;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.helper.dao.DAOHelper;
import com.xwder.app.helper.dao.NativeSQL;
import com.xwder.app.modules.blog.dao.BlogDaoResourceHandler;
import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.modules.blog.repository.TagRepository;
import com.xwder.app.modules.blog.service.intf.TagService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.sysmodules.blog.dto.TagDto;
import com.xwder.app.utils.PageUtil;
import com.xwder.app.utils.SessionUtil;
import com.xwder.app.utils.UpdateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    /**
     * 分页查询 tag 每个分类下博文数量
     *
     * @param tagDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page listTagArticleCountPageData(TagDto tagDto) {
        String querySql = DAOHelper.getSQL(BlogDaoResourceHandler.class, "query_tag_aritcle_count");
        List params = new ArrayList<>();
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
        params.add(sessionUser.getId());
        params.add(sessionUser.getId());
        Pageable pageable = PageRequest.of(tagDto.getPage(), tagDto.getLimit(), null);
        List<Map> rows = NativeSQL.findByNativeSQLPageable(querySql, params, pageable);
        int count = NativeSQL.countByNativeSQL(querySql, params);
        Page page = PageUtil.page(rows, pageable, count);
        return page;
    }

    /**
     * 保存tag
     *
     * @param tag
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Tag saveTag(Tag tag) {
        Tag saveTag = tagRepository.save(tag);
        return saveTag;
    }

    /**
     * 更新tag
     *
     * @param tag
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Tag updateTag(Tag tag) {
        Optional<Tag> tagOptional = tagRepository.findById(tag.getId());
        tagOptional.orElseThrow(() -> new BizException(ResultCode.FAILED.getCode(), "tag 不存在"));
        Tag existTag = tagOptional.get();
        UpdateUtil.copyNullProperties(tag, existTag);
        tagRepository.save(existTag);
        return existTag;
    }

    /**
     * 根据id删除标签 如果该标签存在文章 则不能删除
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CommonResult deleteTagById(Long id) {
        String queryCount = "select id count from blog_article_tag where tag_id = " + id;
        int count = NativeSQL.countByNativeSQL(queryCount, Lists.newArrayList());
        if (count > 0) {
            return CommonResult.failed("删除失败,该分类下存在博客文章不能删除");
        }
        tagRepository.deleteById(id);
        return CommonResult.success(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * 根据id查询tag
     *
     * @param id
     * @return
     */
    @Override
    public Tag findTagById(Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        return optionalTag.get();
    }
}
