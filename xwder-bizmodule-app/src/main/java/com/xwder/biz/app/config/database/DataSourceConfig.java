package com.xwder.biz.app.config.database;

import com.xwder.biz.app.attribute.DataBaseConstant;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingjdbc.core.constant.ShardingPropertiesConstant;
import io.shardingjdbc.core.jdbc.core.datasource.ShardingDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
public class DataSourceConfig {
    @Autowired
    private DatabaseConfig databaseConfig;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Logger logger = LogManager.getLogger(DataSourceConfig.class);

    @Value("${spring.jpa.show-sql:false}")
    private String showSql;

    /**
     * 配置sharding-jdbc的DataSource，给上层应用使用，这个DataSource包含所有的逻辑库和逻辑表，应用增删改查时，修改对应sql
     * 然后选择合适的数据库继续操作。因此这个DataSource创建很重要。
     *
     * @return
     * @throws SQLException
     */
    @Bean
    @Primary
    public DataSource shardingDataSource() throws SQLException {

        ShardingRuleConfiguration shardingRuleConfig = shardingRuleConfiguration();
        Properties properties = shardingJdbcProperties();
        return new ShardingDataSource(shardingRuleConfig.build(createDataSourceMap()),new HashMap<>(),properties);
    }

    @Bean
    public ShardingRuleConfiguration shardingRuleConfiguration(){
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 表配置，可以添加多个配置
        shardingRuleConfig.getTableRuleConfigs().add(bookChapterTableRuleConfiguration());

        shardingRuleConfig.setDefaultDataSourceName(DataBaseConstant.DEFAULT_DB);
        return shardingRuleConfig;
    }

    @Bean(name = "bookChapterTableRuleConfiguration")
    public TableRuleConfiguration bookChapterTableRuleConfiguration() {
        TableRuleConfiguration routeInfoTableRuleConfiguration = new TableRuleConfiguration();
        //设置用户表逻辑
        routeInfoTableRuleConfiguration.setLogicTable(DataBaseConstant.TABEL_PRE_NAME);
        //设置数据节点
        routeInfoTableRuleConfiguration.setActualDataNodes(initbookChapterActualDataNodesFromTable(DataBaseConstant.TABEL_PRE_NAME_));
        // 分库
        //routeInfoTableRuleConfiguration.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(DataBaseConstant.COLUMN_NAME, DatabaseShardingAlgorithm.class.getName(),DatabaseShardingAlgorithm.class.getName()));
        routeInfoTableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(DataBaseConstant.SPLIT_COLUMN_NAME, TableShardingAlgorithm.class.getName(), TableShardingAlgorithm.class.getName()));
        return routeInfoTableRuleConfiguration;
    }

    @Bean(name = "shardingJdbcProperties")
    public Properties shardingJdbcProperties(){
        //配置显示SQL
        Properties properties = new Properties();
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), showSql);
        return properties;
    }

    /**
     * 获取数据源，即包含有多少个数据库，读入到系统中存放于map中
     *
     * @return
     */
    public Map<String, DataSource> createDataSourceMap() throws SQLException {
        Map<String, DataSource> result = new HashMap<>();
        result.put(DataBaseConstant.DEFAULT_DB, databaseConfig.createDataSource());
        return result;
    }


    /**
     * 读取数据的分表的所有表
     * @param tableNamePreFix
     * @return
     */
    private String initbookChapterActualDataNodesFromTable(String tableNamePreFix) {
        StringBuilder stringBuilder = new StringBuilder();
        String databaseSql = "select database()";
        String database = jdbcTemplate.queryForObject(databaseSql, String.class);
        String queryTableSql = "select table_name from information_schema.tables where table_schema = ? and table_name like concat(?,'%')";
        List<String> tableList = jdbcTemplate.queryForList(queryTableSql, String.class, database, tableNamePreFix);
        for (String table : tableList) {
            stringBuilder.append(database).append(".").append(table).append(",");
        }

        for (String table : tableList) {
            stringBuilder.append(DataBaseConstant.DEFAULT_DB.toUpperCase()).append(".").append(table).append(",");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString().toLowerCase();
    }


}