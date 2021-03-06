package com.xwder.app.modules.novel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.common.result.CommonResult;
import com.xwder.app.common.result.ResultCode;
import com.xwder.app.common.jpa.domain.OffsetBasedPageRequest;
import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.repository.BookChapterRepository;
import com.xwder.app.modules.novel.repository.BookInfoRepository;
import com.xwder.app.modules.novel.service.intf.BookChapterService;
import com.xwder.app.modules.novel.service.intf.BookInfoService;
import com.xwder.app.sysmodules.novel.dto.BookInfoDto;
import com.xwder.app.utils.DateUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * bookservice service
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/07
 */
@Service
public class BookInfoServiceImpl implements BookInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String serviceName = "书籍service服务";

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private BookChapterService bookChapterService;

    protected ExecutorService executorService =
            new ThreadPoolExecutor(20,
                    25, 20L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(4000));

    /**
     * 根据书籍名称查询所有书记
     * @param bookName 书籍名称
     * @return 书籍列表
     */
    @Override
    public List<BookInfo> findAllByBookName(String bookName) {
        return bookInfoRepository.findAllByBookName(bookName);
    }

    /**
     * 分页查询书籍信息
     *
     * @param category
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<BookInfo> listBookInfo(String category, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<BookInfo> pageBookInfo;
        // 直接查
        if (StrUtil.isEmpty(category)) {
            pageBookInfo = bookInfoRepository.findAll(pageable);
        } else {
            pageBookInfo = bookInfoRepository.findByCategory(category, pageable);
        }
        return pageBookInfo;
    }

    /**
     * 分页查询书籍信息
     *
     * @return
     */
    @Override
    public Page<BookInfo> listBookInfo(BookInfoDto bookInfoDto) {
        return bookInfoRepository.findAll(bookInfoDto);
    }

    /**
     * 分页查询书籍信息
     *
     * @param bookName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<BookInfo> listBookInfoByBookName(String bookName, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "wordSize");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<BookInfo> pageBookInfo;
        // 直接查
        if (StrUtil.isEmpty(bookName)) {
            pageBookInfo = bookInfoRepository.findAll(pageable);
        } else {
            pageBookInfo = bookInfoRepository.findByBookNameContaining(bookName, pageable);
        }
        return pageBookInfo;
    }

    /**
     * 根据书名获取书籍
     *
     * @param bookName
     * @return
     */
    @Override
    public List<BookInfo> listBookInfoByBookName(String bookName) {
        List<BookInfo> bookInfoList = bookInfoRepository.findAllByBookName(bookName);
        return bookInfoList;
    }

    /**
     * 根据author查询所有书籍
     *
     * @param author
     * @return
     */
    @Override
    public List<BookInfo> listBookInfoByAuthor(String author) {
        List<BookInfo> bookInfoList = bookInfoRepository.findAllByAuthor(author);
        return bookInfoList;
    }

    /**
     * 根据id获取书籍信息
     *
     * @param id
     * @return
     */
    @Override
    public BookInfo getBookInfoById(Integer id) {
        Optional<BookInfo> optional = bookInfoRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 根据bookUrl获取书籍
     *
     * @param bookUrl 书籍url
     * @return 书籍信息
     */
    @Override
    public BookInfo getBookInfoByBookUrl(String bookUrl) {
        return bookInfoRepository.findByBookUrl(bookUrl);
    }

    /**
     * 从本地获取章节内容
     *
     * @param bookId
     * @param chapterId
     * @return
     */
    @Override
    public String getLocalChapterContent(Integer bookId, Integer chapterId) {
        String bookSaveDir = getBookSaveDir(bookId);
        String chapterPath = bookSaveDir + File.separator + chapterId + ".html";
        File chapterFile = new File(chapterPath);
        if (!chapterFile.exists()) {
            return null;
        }
        String chapterContent = FileUtil.readUtf8String(chapterFile);
        return chapterContent;
    }

    /**
     * 根据书名下载书籍
     *
     * @param bookName
     * @return
     */
    @Override
    public CommonResult downBookByBookName(String bookName) {
        List<BookInfo> bookInfoList = bookInfoRepository.findAllByBookName(bookName);
        if (CollectionUtil.isEmpty(bookInfoList)) {
            logger.info("[{}] 未查询到该书籍", serviceName);
            return CommonResult.failed(ResultCode.VALIDATE_FAILED.getCode(), bookName + "  未查询到该书籍");
        }
        return downBook(bookInfoList.get(0));
    }

    @Override
    public CommonResult downBookByBookId(Integer bookId) {
        List<BookInfo> bookInfoList = bookInfoRepository.findAllById(Collections.singleton(bookId));
        if (CollectionUtil.isEmpty(bookInfoList)) {
            logger.info("[{}] 未查询到该书籍", serviceName);
            return CommonResult.failed(ResultCode.VALIDATE_FAILED.getCode(), bookId + "  未查询到该书籍");
        }
        return downBook(bookInfoList.get(0));
    }


    /**
     * 下载书籍服务
     *
     * @param bookInfo
     * @return
     */
    protected CommonResult downBook(BookInfo bookInfo) {
        Long startTime = System.currentTimeMillis();
        String bookName = bookInfo.getBookName();
        logger.info("[{}]下载书籍[{}]开始", serviceName, bookName);

        String bookSaveDir = getBookSaveDir(bookInfo.getId());
        // 获取目录下的txt后缀文件路径 用于判断txt文件是不是最新的 文件名称由 bookid + __ + 章节数量  组成
        File existTxtFile = getExistTxtFile(bookSaveDir);
        // 获取书籍已经获取的章节总数量
        Integer bookId = bookInfo.getId();
        List<BookChapter> bookChapters = bookChapterService.findAllByBookId(bookId);
        if (CollectionUtil.isEmpty(bookChapters)) {
            logger.info("[{}]下载书籍[{}]结束,书籍没有收录章节信息,耗时[{}]", serviceName, bookName,
                    DateUtil.diffTime(startTime, System.currentTimeMillis()));
            return CommonResult.failed(bookName + " 书籍没有收录章节信息");
        }
        if (existTxtFile != null) {
            Integer packageChapterCount = existPackageChapterCount(bookInfo.getId());
            // 判断已经打包的txt章节数量和数据库中的章节数量
            if (packageChapterCount >= bookChapters.size()) {
                HashMap<String, Object> map = Maps.newHashMap();
                map.put("file", existTxtFile);
                map.put("fileName", bookName + ".txt");
                logger.info("[{}]下载书籍[{}]结束,书籍获取成功,耗时[{}]", serviceName, bookName,
                        DateUtil.diffTime(startTime, System.currentTimeMillis()));
                return CommonResult.success(map);
            } else {
                // 重新添加新章节信息到txt文件中
                File packageTxtFile = packageTxtFile(bookInfo);
                if (packageTxtFile != null) {
                    HashMap<String, Object> map = Maps.newHashMap();
                    map.put("file", packageTxtFile);
                    map.put("fileName", bookName + ".txt");
                    logger.info("[{}]下载书籍[{}]结束,书籍获取成功,耗时[{}]", serviceName, bookName,
                            DateUtil.diffTime(startTime, System.currentTimeMillis()));
                    return CommonResult.success(map);
                }
            }
        } else {
            // 从没有打过包
            // 重新添加新章节信息到txt文件中
            File packageTxtFile = packageTxtFile(bookInfo);
            if (packageTxtFile != null) {
                HashMap<String, Object> map = Maps.newHashMap();
                map.put("file", packageTxtFile);
                map.put("fileName", bookName + ".txt");
                logger.info("[{}]下载书籍[{}]结束,书籍获取成功,耗时[{}]", serviceName, bookName,
                        DateUtil.diffTime(startTime, System.currentTimeMillis()));
                return CommonResult.success(map);
            }
        }
        logger.info("[{}]下载书籍[{}]结束,没有收录该书籍信息,耗时[{}]", serviceName, bookName,
                DateUtil.diffTime(startTime, System.currentTimeMillis()));
        return CommonResult.failed(bookName + " 没有收录该书籍信息");
    }

    /**
     * 打包、追加新章节打包 txt文件
     *
     * @param bookInfo
     */
    private File packageTxtFile(BookInfo bookInfo) {
        String bookSaveDir = getBookSaveDir(bookInfo.getId());
        Integer existPackageChapterCount = existPackageChapterCount(bookInfo.getId());
        File existTxtFile = getExistTxtFile(bookSaveDir);

        // 查询需要打包的章节信息
        int limit = 10000;
        int offset = existPackageChapterCount;
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<BookChapter> bookChapters = bookChapterService.findAllByBookIdPageable(bookInfo.getId(), pageable);

        if (CollectionUtil.isNotEmpty(bookChapters)) {
            // 没有打包过
            if (existPackageChapterCount == 0 || existTxtFile == null) {
                existTxtFile = new File(bookSaveDir + File.separator + bookInfo.getId() + "__" + bookChapters.size() + ".txt");
            }
            // 遍历需要添加的章节信息
            int index = 1;

            // 过滤一遍所有章节 查看本地是否有章节文件
            for (BookChapter bookChapter : bookChapters) {
                String chapterDir = bookSaveDir + File.separator + bookChapter.getId() + ".html";
                File chapterFile = new File(chapterDir);
                if (chapterFile.exists()) {
                    String htmlContent = FileUtil.readUtf8String(chapterFile);
                    bookChapter.setChapterContent(htmlContent);
                }
            }

            // 线程池爬取章节内容
            CompletableFuture<Void> all = null;
            for (BookChapter bookChapter : bookChapters) {
                // 定义任务
                CompletableFuture<BookChapter> cf = CompletableFuture.supplyAsync(() -> {
                    try {
                        // 爬取内容保存数据
                        bookChapterService.spiderChapterContent(bookChapter);
                        // 保存文件
                        String chapterDir = bookSaveDir + File.separator + bookChapter.getId() + ".html";
                        File bookSaveDirFile = new File(bookSaveDir);
                        if (!bookSaveDirFile.exists()) {
                            bookSaveDirFile.mkdirs();
                        }
                        File file = new File(chapterDir);
                        if ((!file.exists()) && StrUtil.isNotEmpty(bookChapter.getChapterContent())) {

                            FileUtil.writeBytes(bookChapter.getChapterContent().getBytes(), file);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("获取章节详情失败，章节id[{}],sourceUrl [{}],错误信息[{}]", bookChapter.getId(), bookChapter.getSourceUrl(), e);
                    }
                    return bookChapter;
                }, executorService);

                all = CompletableFuture.allOf(cf);
            }
            // 开始等待所有任务执行完成
            all.join();

            for (BookChapter bookChapter : bookChapters) {
                String strChapterContent = htmlConvertTxt(bookChapter.getChapterContent());
                FileUtil.appendUtf8String(strChapterContent, existTxtFile);

                // 修改文件名称
                existPackageChapterCount = existPackageChapterCount + 1;
                String newFileName = existTxtFile.getParentFile().getAbsolutePath() + File.separator +
                        bookChapter.getBookId() + "__" + existPackageChapterCount + ".txt";
                File newExistTxtFile = new File(newFileName);
                existTxtFile.renameTo(newExistTxtFile);
                existTxtFile = newExistTxtFile;

                FileUtil.appendUtf8Lines(Arrays.asList(""), existTxtFile);

            }
            return existTxtFile;
        }

        // 没有新章节无需再次打包; 或者是没有该数据信息没查询到章节信息
        return null;
    }

    /**
     * 获取 已经打包的章节数量，如果没有打包过返回0
     *
     * @param bookId
     * @return
     */
    private Integer existPackageChapterCount(Integer bookId) {
        String bookSaveDir = getBookSaveDir(bookId);
        // 获取目录下的txt后缀文件路径 用于判断txt文件是不是最新的 文件名称由 bookid + __ + 章节数量  组成
        File existTxtFile = getExistTxtFile(bookSaveDir);
        if (existTxtFile == null) {
            return 0;
        }
        String txtFileName = existTxtFile.getName();
        String preName = txtFileName.split("\\.")[0];
        String strChapterCount = preName.split("__")[1];
        return Integer.parseInt(strChapterCount);
    }

    /**
     * 根据bookid 获取书籍的书籍目录
     *
     * @param bookId
     * @return
     */
    private String getBookSaveDir(Integer bookId) {
        String bookDir = "";
        if (bookId <= 1000) {
            bookDir = "1000";
        } else {
            if (bookId % 1000 == 0) {
                bookDir = bookId + "";
            } else {
                bookDir = (bookId / 1000 + 1) * 1000 + "";
            }
        }
        return sysConfigAttribute.getNovelBookDir() + File.separator + bookDir + File.separator + bookId;
    }

    /**
     * 获取已经打包的txt文件
     *
     * @param bookPath
     * @return
     */
    private File getExistTxtFile(String bookPath) {
        File bookDirFile = new File(bookPath);
        if (bookDirFile.exists()) {
            File[] files = bookDirFile.listFiles();
            List<File> fileList = Arrays.asList(files);
            for (File file : fileList) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt")) {
                        return file;
                    }
                }
            }
        }
        return null;
    }

    /**
     * html 转 txt 文档
     *
     * @param html
     * @return
     */
    private String htmlConvertTxt(String html) {
        if (StrUtil.isEmpty(html)) {
            return "";
        }
        Document document = Jsoup.parse(html);
        Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        document.outputSettings(outputSettings);
        document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        document.select("p").append("\\n");
        String newHtml = document.html().replaceAll("\\\\n", "\n");
        String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
        String result = StringEscapeUtils.unescapeHtml(plainText.trim());
        return result;
    }


}
