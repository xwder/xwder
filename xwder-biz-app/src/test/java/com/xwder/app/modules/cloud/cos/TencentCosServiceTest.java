package com.xwder.app.modules.cloud.cos;

import com.xwder.app.XwderApplication;
import com.xwder.app.modules.cloud.tencent.cos.service.TencentCosService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderApplication.class)
public class TencentCosServiceTest {

    @Autowired
    private TencentCosService tencentCosService;

    @Test
    public void deleteCosFileTest(){
        String key = "image/blog/xwder/1-20201010161309386.png";
        tencentCosService.deleteCosFile(null,null,key);
    }

}
