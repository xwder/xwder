package com.xwder.app.modules.picture.repository;

import com.xwder.app.XwderApplication;
import com.xwder.app.modules.picture.entity.Picture;
import com.xwder.app.modules.picture.service.intf.PictureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xwder
 * @date 2021/5/12 14:41
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderApplication.class)
public class PictureRepositoryTest {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private PictureService pictureService;

    @Test
    public void insertPicture() {
        List list = new ArrayList<>(200);
        for (int i = 0; i < 197; i++) {
            String url = "https://cdn.xwder.com/picture/2cygirl/" + i + ".jpg";
            Picture picture = new Picture();
            picture.setCategoryId(1L);
            picture.setCdnUrl(url);
            picture.setUserId(1L);
            picture.setTitle("二次元少女");
            picture.setGmtCreate(new Date());
            list.add(picture);
        }
        pictureService.saveAll(list);
    }
}
