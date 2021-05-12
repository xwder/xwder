package com.xwder.app.modules.picture.controller;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.modules.picture.dto.PictureDto;
import com.xwder.app.modules.picture.entity.Picture;
import com.xwder.app.modules.picture.service.intf.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xwder
 * @date 2021/5/12 15:47
 **/
@Controller
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/index")
    public String index(Model model) {
        PictureDto pictureDto = new PictureDto();
        pictureDto.setLimit(20);
        pictureDto.setPage(1);
        Page<Picture> page = pictureService.listPicture(pictureDto);
        model.addAttribute("picturePage",page);
        return "picture/index";
    }

    /**
     * 分页查询 pic list
     *
     * @param pictureDto
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public CommonResult list(PictureDto pictureDto) {
        Page<Picture> page = pictureService.listPicture(pictureDto);
        return CommonResult.success(page);
    }

}
