package com.xwder.app.sysmodules.novel.contorller;

import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.service.intf.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xwder
 * @Description: novel controller
 * @date 2020/9/3 22:57
 */
@RequestMapping(value = "/sys/novel")
@Controller
public class NovelController {

    @Autowired
    private BookInfoService bookInfoService;

    @RequestMapping(value = "/listPage")
    public String listBooInfoPage() {
        return "sys/novel/novelList";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public Object listBookInfo() {
        Page<BookInfo> bookInfos = bookInfoService.listBookInfo(null, 1, 20);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",1000);
        map.put("msg","msg");
        map.put("data",bookInfos.getContent());
        return map;
    }

}
