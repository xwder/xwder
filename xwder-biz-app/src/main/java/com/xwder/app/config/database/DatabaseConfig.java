//package com.xwder.app.config.database;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//public class DatabaseConfig {
//
//    public DataSource dataSource;
//
//    private String dataBaseName="xwder";
//
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//    @Value("${spring.datasource.initialSize}")
//    private Integer initialSize;
//    @Value("${spring.datasource.minIdle}")
//    private Integer minIdle;
//    @Value("${spring.datasource.maxActive}")
//    private Integer maxActive;
//    @Value("${spring.datasource.maxWait}")
//    private Integer maxWait;
//    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
//    private Long timeBetweenEvictionRunsMillis;
//    @Value("${spring.datasource.validationQuery}")
//    private String validationQuery;
//    @Value("${spring.datasource.testWhileIdle}")
//    private Boolean testWhileIdle;
//    @Value("${spring.datasource.testOnBorrow}")
//    private Boolean testOnBorrow;
//    @Value("${spring.datasource.testOnReturn}")
//    private Boolean testOnReturn;
//
//    public DataSource createDataSource() {
//
//        if (dataSource != null && (dataSource instanceof DruidDataSource)) {
//            return dataSource;
//        }
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(driverClassName);
//        druidDataSource.setUrl(url);
//        druidDataSource.setUsername(username);
//        druidDataSource.setPassword(password);
//        druidDataSource.setInitialSize(initialSize);
//        druidDataSource.setMinIdle(minIdle);
//        druidDataSource.setMaxActive(maxActive);
//        druidDataSource.setMaxWait(maxWait);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        druidDataSource.setValidationQuery(validationQuery);
//        druidDataSource.setTestWhileIdle(testWhileIdle);
//        druidDataSource.setTestOnBorrow(testOnBorrow);
//        druidDataSource.setTestOnReturn(testOnReturn);
//        return druidDataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(createDataSource());
//    }
//
//    public String getDatabaseName() {
//        return dataBaseName;
//    }
//}