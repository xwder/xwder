package com.xwder.app.dao;

import com.google.common.collect.Lists;
import com.xwder.app.XwderApplication;
import com.xwder.app.helper.dao.NativeSQL;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.repository.CategoryRepository;
import com.xwder.app.sysmodules.dbbak.task.DbBakTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DbBakTask dbBakTask;

    @Test
    @Transactional
    public void daoTest() {
        String sql = "select count(*) from blog_category";
        sql = " select * from (\n" +
                "		 SELECT\n" +
                "					t.id,\n" +
                "					t.user_id,\n" +
                "					t.category_name,\n" +
                "					IFNULL( t1.count, 0 ) count\n" +
                "		 FROM\n" +
                "					( SELECT * FROM blog_category WHERE user_id = 1 AND is_available = 1 ) t\n" +
                "					LEFT JOIN\n" +
                "					( SELECT category_id, count( category_id ) count FROM blog_article WHERE user_id = 1 AND STATUS = 1 AND is_available = 1 GROUP BY category_id ) t1  ON t.id = t1.category_id\n" +
                "		 ORDER BY\n" +
                "					t1.count DESC\n" +
                "	) t where t.count > 0";
        List<Map> mapList = NativeSQL.findByNativeSQL(sql, null, null);

        List<Category> categoryList = categoryRepository.findByUserId(1L);

        List<Map> maps = categoryRepository.listCagetroyCount(1L);

        System.out.println(mapList);
    }

    /**
     * schema 测试
     */
    @Test
    public void dbSchemaTest() {
        String queryTableNameSql = "SELECT table_name FROM information_schema.TABLES " +
                "WHERE table_schema = 'xwder' and table_name not like 'book_chapter%' and table_name != 'sys_job_log'";
        List<String> tableNames = NativeSQL.queryForList(queryTableNameSql, String.class, null);
    }

    @Test
    public void dbBakTest() {
        dbBakTask.dbBakAndSendMailServiceEntry();
    }

}
