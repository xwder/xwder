package com.xwder.app.helper.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.xwder.app.consts.DataBaseConstant;
import com.xwder.app.utils.ObjectUtil;
import com.xwder.app.utils.XMLUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
public class BaseDaoResourceHandler implements InitializingBean {
    public static Map<String, String> DAO_RESOURCE_CACHE = new ConcurrentHashMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        String classPath = ObjectUtil.getSlashClassPath(this.getClass());
        String daoXmlClassPath = classPath + "daoMapper.xml";

        Document xmlDoc = XMLUtil.createDoc(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(daoXmlClassPath));

        List<Node> daoList = xmlDoc.selectNodes("/mapper/sql");

        if (CollectionUtil.isNotEmpty(daoList)) {
            for (int i = 0; i < daoList.size(); i++) {
                Element element = (Element) daoList.get(i);

                String sqlId = element.attribute("id").getValue();

                Element commonElement = element.element(DataBaseConstant.DBTYPE_COMMON);
                Element oracleElement = element.element(DataBaseConstant.DBTYPE_ORACLE);
                Element mysqlElement = element.element(DataBaseConstant.DBTYPE_MYSQL);

                String commonStr = (commonElement != null) ? commonElement.getTextTrim() : "";
                String oracleStr = (oracleElement != null) ? oracleElement.getTextTrim() : "";
                String mysqlStr = (mysqlElement != null) ? mysqlElement.getTextTrim() : "";

                DAOHelper.putSQL(this.getClass(), sqlId, DataBaseConstant.DBTYPE_COMMON, commonStr);
                DAOHelper.putSQL(this.getClass(), sqlId, DataBaseConstant.DBTYPE_ORACLE, oracleStr);
                DAOHelper.putSQL(this.getClass(), sqlId, DataBaseConstant.DBTYPE_MYSQL, mysqlStr);
            }
        }
    }
}

