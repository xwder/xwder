package com.xwder.app.modules.picture.service.impl;

import com.xwder.app.modules.picture.dto.PictureDto;
import com.xwder.app.modules.picture.entity.Picture;
import com.xwder.app.modules.picture.repository.PictureRepository;
import com.xwder.app.modules.picture.service.intf.PictureService;
import com.xwder.app.utils.TimeCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xwder
 * @date 2021/5/12 15:13
 **/
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void save(Picture picture) {
        pictureRepository.save(picture);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveAll(List list) {
        pictureRepository.saveAll(list);
    }

    @Override
    public Page<Picture> listPicture(PictureDto pictureDto) {
        Page<Picture> page = pictureRepository.findAll(pictureDto);
        if (page != null) {
            page.getContent().forEach(x->x.setCreateTimeDesc(TimeCountUtil.format(x.getGmtCreate())));
        }
        return page;
    }
}
