package com.xwder.app.dao;

import com.xwder.app.XwderApplication;
import com.xwder.app.helper.dao.NativeSQL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderApplication.class)
public class DaoTest {

    @Test
    @Transactional(readOnly = true)
    public void daoTest() {
        String sql = "select count(*) from blog_category";
        List<Map> mapList = NativeSQL.findByNativeSQL(sql, null, null);
        System.out.println(mapList);
    }

}
