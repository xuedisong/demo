package com.cloudhua.imageplatform.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;


/**
 * Created by yangchao on 2017/8/14.
 */
@Configuration//@Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext(MyBatisConfig.class)替换ClassPathXmlApplicationContext("spring-context.xml")+MyBatisConfig.class实例化，相当于少一步配置头<beans> </beans>
@EnableTransactionManagement//注解开启注解式事务的支持
public class MyBatisConfig implements TransactionManagementConfigurer {

    private Logger logger = Logger.getLogger(MyBatisConfig.class);//日志

    @Autowired
    private DataSource dataSource;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.cloudhua.domain");
        try {
            return bean.getObject();
        } catch (Exception e) {
            logger.error("get object has something error.", e);
            throw e;
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
