package com.lrelia.crawler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/4
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "customerEntityManagerFactory",
        transactionManagerRef = "transactionManagerDb",
        basePackages = "com.lrelia.crawler.repository")
public class DataSouce {
    @Autowired
    private DbConfig dbConfig;


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource inventoryDataSource() {
        return DataSourceBuilder.create()
                .url(dbConfig.getUrl())
                .username(dbConfig.getUserName())
                .password(dbConfig.getPassword())
                .build();


    }

    @Bean
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(inventoryDataSource())
                .packages("com.lrelia.crawler.entity")
                .build();
    }

    @Bean("transactionManagerDb")
    public PlatformTransactionManager transactionManagerInventory(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(customerEntityManagerFactory(builder).getObject());
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return customerEntityManagerFactory(builder).getObject().createEntityManager();
    }
}
