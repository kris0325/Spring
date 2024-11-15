package com.google.springboot.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.session.DefaultSessionFactory;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.net.InetSocketAddress;

@Configuration
@EnableCassandraRepositories(basePackages = "com.google.springboot.cassandrarepository") // 指定扫描的 repository 包
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "ecommerce"; // 替换为你的 keyspace 名称
    }

    @Override
    protected String getContactPoints() {
        return "localhost"; // 本地Cassandra服务的地址
    }

    @Override
    protected int getPort() {
        return 9042; // 默认Cassandra端口
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS; // Schema同步策略
    }

    @Bean
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(getContactPoints());
        session.setPort(getPort());
        session.setKeyspaceName(getKeyspaceName());
        session.setLocalDatacenter("datacenter1"); // 设置本地数据中心名称
        return session;
    }
}
