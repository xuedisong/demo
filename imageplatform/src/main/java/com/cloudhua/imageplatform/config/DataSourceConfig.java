package com.cloudhua.imageplatform.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Created by yangchao on 2017/8/14.
 */
@Configuration
public class DataSourceConfig {
    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.db}")
    private String db;

    @Value("${jdbc.params}")
    private String params;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.maxActive}")
    private int maxActive;

    @Value("${jdbc.maxIdel}")
    private int maxIdel;

    @Value("${jdbc.maxWait}")
    private long maxWait;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        String connectUrl = url + "/" + db + "?" + params;
        dataSource.setUrl(connectUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxConnLifetimeMillis(maxActive);
        dataSource.setMaxIdle(maxIdel);
        dataSource.setMaxWaitMillis(maxWait);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }
}
