package com.xwder.app.modules.novel.controller;

import cn.hutool.core.util.StrUtil;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.service.intf.BookInfoService;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.api.ResultCode;
import com.xwder.cloud.commmon.constan.Constant;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * bookinfo controller
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/07
 */
@Validated
@RequestMapping(value = "/book")
@Controller
public class BookInfoController {

    @Autowired
    private BookInfoService bookInfoService;

    @ResponseBody
    @RequestMapping(value = "")
    public CommonResult getBookInfoByBookName(@RequestParam("bookName") String bookName) {
        List<BookInfo> bookInfoList = bookInfoService.listBookInfoByBookName(bookName);
        return CommonResult.success(bookInfoList);
    }

    @ResponseBody
    @RequestMapping(value = "/down")
    public Object downBook(
            @RequestParam(value = "bookName", required = false) String bookName,
            @RequestParam(value = "bookId", required = false) @Min(1) @Max(200000) Integer bookId
    ) throws Exception {
        CommonResult commonResult = CommonResult.failed();
        if (StrUtil.isNotEmpty(bookName)) {
            commonResult = bookInfoService.downBookByBookName(bookName);
        }
        if (bookId != null) {
            commonResult = bookInfoService.downBookByBookId(bookId);
        }
        if (commonResult.getCode() == ResultCode.SUCCESS.getCode() && commonResult.getData() != null) {
            Map map = (Map) commonResult.getData();
            File file = (File) map.get("file");
            String bookNameFile = (String) map.get("fileName");
            //获取文件名
            String fileName = file.getName();
            //创建springframework的HttpHeaders对象
            HttpHeaders headers = new HttpHeaders();
            //下载显示的文件名,解决文件名称乱码问题
            String downLoadFileName = new String(bookNameFile.getBytes("UTF-8"), "iso-8859-1");
            //通知浏览器以attachment（下载方式）打开文件
            headers.setContentDispositionFormData("attachment", downLoadFileName);
            //application/octet-stream:二进制流数据(最常见的文件下载)
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //返回状态码：201 HttpStatus.CREATED :请求已经被实现，而且有一个新的资源已经依据请求的需要而建立，且其 URI 已经随Location 头信息返回
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        }
        return commonResult;
    }

    /**
     * 书籍模块首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String toBookIndex(@RequestParam(value = "category", required = false) String category,
                              @RequestParam(value = "bookName", required = false) String bookName,
                              @RequestParam(value = "pageNum", required = false) @Min(1) @Max(10000) Integer pageNum,
                              @RequestParam(value = "pageSize", required = false) @Min(1) @Max(50) Integer pageSize,
                              Model model) {
        pageNum = pageNum == null ? Constant.DEFAULT_PAGE_NUM : pageNum;
        pageSize = pageSize == null ? Constant.DEFAULT_PAGE_SIZE + 1 : pageSize;
        Page<BookInfo> bookInfoPage;
        if (StrUtil.isNotEmpty(bookName)) {
            model.addAttribute("bookName", bookName);
            bookInfoPage = bookInfoService.listBookInfoByBookName(bookName, pageNum, pageSize);
        } else {
            bookInfoPage = bookInfoService.listBookInfo(category, pageNum, pageSize);
        }
        model.addAttribute("bookInfos", bookInfoPage.getContent());
        model.addAttribute("startNum", pageSize * (pageNum - 1) + 1);
        model.addAttribute("endNum", pageSize * pageNum);
        model.addAttribute("totalNum", bookInfoPage.getTotalElements());
        model.addAttribute("totalPages", bookInfoPage.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("bookInfoPage", bookInfoPage);
        model.addAttribute("category", category);

        return "book/index";
    }

}
