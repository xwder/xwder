package com.xwder.manage.modules.pic.plmm.controller;

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
import com.xwder.manage.modules.pic.plmm.domain.ImagePlmm;
import com.xwder.manage.modules.pic.plmm.service.IImagePlmmService;
import com.xwder.manage.common.core.controller.BaseController;
import com.xwder.manage.common.core.domain.AjaxResult;
import com.xwder.manage.common.utils.poi.ExcelUtil;
import com.xwder.manage.common.core.page.TableDataInfo;

/**
 * 美女图片Controller
 * 
 * @author xwder
 * @date 2020-02-07
 */
@Controller
@RequestMapping("/plmm/plmm")
public class ImagePlmmController extends BaseController
{
    private String prefix = "modules/image/plmm";

    @Autowired
    private IImagePlmmService imagePlmmService;

    @RequiresPermissions("plmm:plmm:view")
    @GetMapping()
    public String plmm()
    {
        return prefix + "/plmm";
    }

    /**
     * 查询美女图片列表
     */
    @RequiresPermissions("plmm:plmm:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ImagePlmm imagePlmm)
    {
        startPage();
        List<ImagePlmm> list = imagePlmmService.selectImagePlmmList(imagePlmm);
        return getDataTable(list);
    }

    /**
     * 导出美女图片列表
     */
    @RequiresPermissions("plmm:plmm:export")
    @Log(title = "美女图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ImagePlmm imagePlmm)
    {
        List<ImagePlmm> list = imagePlmmService.selectImagePlmmList(imagePlmm);
        ExcelUtil<ImagePlmm> util = new ExcelUtil<ImagePlmm>(ImagePlmm.class);
        return util.exportExcel(list, "plmm");
    }

    /**
     * 新增美女图片
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存美女图片
     */
    @RequiresPermissions("plmm:plmm:add")
    @Log(title = "美女图片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ImagePlmm imagePlmm)
    {
        return toAjax(imagePlmmService.insertImagePlmm(imagePlmm));
    }

    /**
     * 修改美女图片
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ImagePlmm imagePlmm = imagePlmmService.selectImagePlmmById(id);
        mmap.put("imagePlmm", imagePlmm);
        return prefix + "/edit";
    }

    /**
     * 修改保存美女图片
     */
    @RequiresPermissions("plmm:plmm:edit")
    @Log(title = "美女图片", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ImagePlmm imagePlmm)
    {
        return toAjax(imagePlmmService.updateImagePlmm(imagePlmm));
    }

    /**
     * 删除美女图片
     */
    @RequiresPermissions("plmm:plmm:remove")
    @Log(title = "美女图片", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(imagePlmmService.deleteImagePlmmByIds(ids));
    }
}
