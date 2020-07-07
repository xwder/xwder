package com.xwder.biz.app.modules.novel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.xwder.biz.app.modules.novel.entity.BookChapter;
import com.xwder.biz.app.modules.novel.entity.BookInfo;
import com.xwder.biz.app.modules.novel.repository.BookChapterRepository;
import com.xwder.biz.app.modules.novel.repository.BookInfoRepository;
import com.xwder.biz.app.modules.novel.service.intf.BookChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xwder
 * @Description: 章节服务实现类
 * @date 2020/7/7 20:31
 */
@Service
public class BookChapterServiceImpl implements BookChapterService {

    @Autowired
    private BookChapterRepository bookChapterRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    /**
     * 根据bookId分页查询章节信息
     * @param bookId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookChapter> listBookChapterByBookId(Integer bookId, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, sort);
        return bookChapterRepository.findByBookId(bookId, pageable);
    }

    /**
     * 根据bookName分页查询章节信息
     * @param bookName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookChapter> listBookChapterByBookName(String bookName, Integer pageNum, Integer pageSize) {
        List<BookInfo> bookInfoList = bookInfoRepository.findAllByBookName(bookName);
        if (CollectionUtil.isNotEmpty(bookInfoList)) {
            BookInfo bookInfo = bookInfoList.get(0);
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(pageNum-1, pageSize, sort);
            return bookChapterRepository.findByBookId(bookInfo.getId(), pageable);
        }
        return null;
    }
}
