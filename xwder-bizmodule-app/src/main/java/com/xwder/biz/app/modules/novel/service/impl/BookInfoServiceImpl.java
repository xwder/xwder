package com.xwder.biz.app.modules.novel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Maps;
import com.xwder.biz.app.attribute.SysConfigAttribute;
import com.xwder.biz.app.common.OffsetBasedPageRequest;
import com.xwder.biz.app.modules.novel.entity.BookChapter;
import com.xwder.biz.app.modules.novel.entity.BookInfo;
import com.xwder.biz.app.modules.novel.repository.BookChapterRepository;
import com.xwder.biz.app.modules.novel.repository.BookInfoRepository;
import com.xwder.biz.app.modules.novel.service.intf.BookInfoService;
import com.xwder.biz.app.utils.DateUtil;
import com.xwder.cloud.commmon.api.CommonResult;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private BookChapterRepository bookChapterRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Override
    public CommonResult downBookByBookName(String bookName) {
        List<BookInfo> bookInfoList = bookInfoRepository.findAllByBookName(bookName);
        if (CollectionUtil.isEmpty(bookInfoList)) {
            logger.info("[{}] 为查询到该书籍", serviceName);
            return CommonResult.success(bookName + " 书籍没有收录章节信息");
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
        List<BookChapter> bookChapters = bookChapterRepository.findAllByBookId(bookId);
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
        List<BookChapter> bookChapters = bookChapterRepository.findAllByBookId(bookInfo.getId(), pageable);

        if (CollectionUtil.isNotEmpty(bookChapters)) {
            // 没有打包过
            if (existPackageChapterCount == 0 || existTxtFile == null) {
                existTxtFile = new File(bookSaveDir + File.separator + bookInfo.getId() + "__" + bookChapters.size() + ".txt");
            }
            // 遍历需要添加的章节信息
            int index = 1;
            for (BookChapter bookChapter : bookChapters) {
                String chapterDir = bookSaveDir + File.separator + bookChapter.getId() + ".html";
                File ChapterFile = new File(chapterDir);
                if (ChapterFile.exists() && ChapterFile.isFile()) {
                    String chapterName = bookChapter.getChapterName();
                    if (!(chapterName.contains("第") || chapterName.contains("章"))) {
                        chapterName = "第" + index + "章 " + chapterName;
                    }
                    FileUtil.appendUtf8Lines(Arrays.asList(chapterName), existTxtFile);

                    String htmlContent = FileUtil.readUtf8String(ChapterFile);
                    String strChapterContent = htmmConvertTxt(htmlContent);
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
    private String htmmConvertTxt(String html) {
        if (StringUtil.isEmpty(html)) {
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
