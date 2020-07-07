package com.xwder.biz.app.modules.novel.controller;

import com.xwder.biz.app.modules.novel.service.intf.BookInfoService;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.api.ResultCode;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/07
 */
@RequestMapping(value = "/book")
@RestController
public class BookInfoController {

    @Autowired
    private BookInfoService bookInfoService;

    @RequestMapping(value="/down")
    public Object downBook(String bookName) throws Exception {
        CommonResult commonResult = bookInfoService.downBookByBookName(bookName);
        if (commonResult.getCode()== ResultCode.SUCCESS.getCode()) {
            Map map = (Map) commonResult.getData();
            File file = (File) map.get("file");
            //获取文件名
            String fileName = file.getName();
            //创建springframework的HttpHeaders对象
            HttpHeaders headers = new HttpHeaders();
            //下载显示的文件名,解决文件名称乱码问题
            String downLoadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            //通知浏览器以attachment（下载方式）打开文件
            headers.setContentDispositionFormData("attachment", downLoadFileName);
            //application/octet-stream:二进制流数据(最常见的文件下载)
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //返回状态码：201 HttpStatus.CREATED :请求已经被实现，而且有一个新的资源已经依据请求的需要而建立，且其 URI 已经随Location 头信息返回
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        }
        return commonResult;
    }

}
