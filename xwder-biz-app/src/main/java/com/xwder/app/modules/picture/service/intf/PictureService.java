package com.xwder.app.modules.picture.service.intf;

import com.xwder.app.modules.picture.dto.PictureDto;
import com.xwder.app.modules.picture.entity.Picture;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xwder
 * @date 2021/5/12 15:12
 **/
public interface PictureService {

    /**
     * 保存图片
     *
     * @param picture 图片信息
     */
    void save(Picture picture);

    /**
     * 批量保存
     *
     * @param list
     */
    void saveAll(List list);

    /**
     * 分页查询
     *
     * @param pictureDto
     * @return
     */
    Page<Picture> listPicture(PictureDto pictureDto);
}
