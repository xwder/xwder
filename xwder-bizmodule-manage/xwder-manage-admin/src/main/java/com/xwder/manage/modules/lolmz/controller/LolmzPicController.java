package com.xwder.manage.modules.lolmz.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xwder.manage.common.annotation.Log;
import com.xwder.manage.common.enums.BusinessType;
import com.xwder.manage.modules.lolmz.domain.LolmzPic;
import com.xwder.manage.modules.lolmz.service.ILolmzPicService;
import com.xwder.manage.common.core.controller.BaseController;
import com.xwder.manage.common.core.domain.AjaxResult;
import com.xwder.manage.common.utils.poi.ExcelUtil;
import com.xwder.manage.common.core.page.TableDataInfo;

/**
 * 英雄联盟Controller
 * 
 * @author xwder
 * @date 2020-02-03
 */
@Controller
@RequestMapping("/lolmz/lolmz")
public class LolmzPicController extends BaseController
{
    private String prefix = "modules/lol/lolmz";

    @Autowired
    private ILolmzPicService lolmzPicService;

    @RequiresPermissions("lolmz:lolmz:view")
    @GetMapping()
    public String lolmz()
    {
        return prefix + "/lolmz";
    }

    /**
     * 查询英雄联盟列表
     */
    @RequiresPermissions("lolmz:lolmz:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(LolmzPic lolmzPic)
    {
        startPage();
        List<LolmzPic> list = lolmzPicService.selectLolmzPicList(lolmzPic);
        return getDataTable(list);
    }

    /**
     * 导出英雄联盟列表
     */
    @RequiresPermissions("lolmz:lolmz:export")
    @Log(title = "英雄联盟", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(LolmzPic lolmzPic)
    {
        List<LolmzPic> list = lolmzPicService.selectLolmzPicList(lolmzPic);
        ExcelUtil<LolmzPic> util = new ExcelUtil<LolmzPic>(LolmzPic.class);
        return util.exportExcel(list, "lolmz");
    }

    /**
     * 新增英雄联盟
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存英雄联盟
     */
    @RequiresPermissions("lolmz:lolmz:add")
    @Log(title = "英雄联盟", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(LolmzPic lolmzPic)
    {
        return toAjax(lolmzPicService.insertLolmzPic(lolmzPic));
    }

    /**
     * 修改英雄联盟
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        LolmzPic lolmzPic = lolmzPicService.selectLolmzPicById(id);
        mmap.put("lolmzPic", lolmzPic);
        return prefix + "/edit";
    }

    /**
     * 修改保存英雄联盟
     */
    @RequiresPermissions("lolmz:lolmz:edit")
    @Log(title = "英雄联盟", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(LolmzPic lolmzPic)
    {
        return toAjax(lolmzPicService.updateLolmzPic(lolmzPic));
    }

    /**
     * 删除英雄联盟
     */
    @RequiresPermissions("lolmz:lolmz:remove")
    @Log(title = "英雄联盟", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(lolmzPicService.deleteLolmzPicByIds(ids));
    }
}
