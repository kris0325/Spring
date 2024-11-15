package com.google.springboot.config;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
 * @Create 2024-10-19 12:22
 * @Description
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.google.springboot.postgresqlrepository",  // PostgreSQL的Repository
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager"
)
@EntityScan(basePackages = "com.google.springboot.model.postgresql") // 实体类所在包
public class PostgresConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("spring.datasource.postgres.driver-class-name"))
                .url(env.getProperty("spring.datasource.postgres.url"))
                .username(env.getProperty("spring.datasource.postgres.username"))
                .password(env.getProperty("spring.datasource.postgres.password"))
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(postgresDataSource());
        em.setPackagesToScan("com.google.springboot.model.postgresql");  // 实体类所在包

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.dialect", env.getProperty("spring.jpa.postgres.properties.hibernate.dialect"));
        additionalProperties.put("hibernate.show_sql", env.getProperty("spring.jpa.postgres.show-sql"));
        additionalProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.postgres.hibernate.ddl-auto"));
        em.setJpaProperties(additionalProperties);

        return em;
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(postgresEntityManagerFactory().getObject());
        return transactionManager;
    }
}

