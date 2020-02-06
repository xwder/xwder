package com.xwder.manage.modules.pic.yt.controller;

import com.xwder.manage.common.annotation.Log;
import com.xwder.manage.common.core.controller.BaseController;
import com.xwder.manage.common.core.domain.AjaxResult;
import com.xwder.manage.common.core.page.TableDataInfo;
import com.xwder.manage.common.enums.BusinessType;
import com.xwder.manage.common.utils.poi.ExcelUtil;
import com.xwder.manage.modules.pic.yt.domain.ImageYt278com;
import com.xwder.manage.modules.pic.yt.service.IImageYt278comService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 妹子图图Controller
 * 
 * @author xwder
 * @date 2020-02-04
 */
@Controller
@RequestMapping("/yt/yt")
public class ImageYt278comController extends BaseController
{
    private String prefix = "modules/image/yt";


    @Autowired
    private IImageYt278comService imageYt278comService;

    @RequiresPermissions("yt:yt:view")
    @GetMapping()
    public String yt()
    {
        return prefix + "/yt";
    }

    /**
     * 查询妹子图图列表
     */
    @RequiresPermissions("yt:yt:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ImageYt278com imageYt278com)
    {
        startPage();
        List<ImageYt278com> list = imageYt278comService.selectImageYt278comList(imageYt278com);
        return getDataTable(list);
    }

    /**
     * 导出妹子图图列表
     */
    @RequiresPermissions("yt:yt:export")
    @Log(title = "妹子图图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ImageYt278com imageYt278com)
    {
        List<ImageYt278com> list = imageYt278comService.selectImageYt278comList(imageYt278com);
        ExcelUtil<ImageYt278com> util = new ExcelUtil<ImageYt278com>(ImageYt278com.class);
        return util.exportExcel(list, "yt");
    }

    /**
     * 新增妹子图图
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存妹子图图
     */
    @RequiresPermissions("yt:yt:add")
    @Log(title = "妹子图图", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ImageYt278com imageYt278com)
    {
        return toAjax(imageYt278comService.insertImageYt278com(imageYt278com));
    }

    /**
     * 修改妹子图图
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ImageYt278com imageYt278com = imageYt278comService.selectImageYt278comById(id);
        mmap.put("imageYt278com", imageYt278com);
        return prefix + "/edit";
    }

    /**
     * 修改保存妹子图图
     */
    @RequiresPermissions("yt:yt:edit")
    @Log(title = "妹子图图", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ImageYt278com imageYt278com)
    {
        return toAjax(imageYt278comService.updateImageYt278com(imageYt278com));
    }

    /**
     * 删除妹子图图
     */
    @RequiresPermissions("yt:yt:remove")
    @Log(title = "妹子图图", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(imageYt278comService.deleteImageYt278comByIds(ids));
    }
}
