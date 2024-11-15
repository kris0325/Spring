package com.google.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import java.util.Properties;
/**
 * @Author kris
 * @Create 2024-10-19 23:08
 * @Description
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.google.springboot.mysqlrepository",  // MySQL的Repository
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
public class MySQLConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("spring.datasource.mysql.driver-class-name"))
                .url(env.getProperty("spring.datasource.mysql.url"))
                .username(env.getProperty("spring.datasource.mysql.username"))
                .password(env.getProperty("spring.datasource.mysql.password"))
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(mysqlDataSource());
        em.setPackagesToScan("com.google.springboot.model.mysql");  // 实体类所在包

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.dialect", env.getProperty("spring.jpa.mysql.properties.hibernate.dialect"));
        additionalProperties.put("hibernate.show_sql", env.getProperty("spring.jpa.mysql.show-sql"));
        additionalProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.mysql.hibernate.ddl-auto"));
        em.setJpaProperties(additionalProperties);

        return em;
    }

    @Bean
    public PlatformTransactionManager mysqlTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mysqlEntityManagerFactory().getObject());
        return transactionManager;
    }
}

