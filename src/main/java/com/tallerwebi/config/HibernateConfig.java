package com.tallerwebi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:db_");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }


//        @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//
//        String dbHost = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
//        String dbPort = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
//        String dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "data_futcode";
//        String dbUser = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
//        String dbPassword = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "";
//
//        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName +
//          "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=30000";
//        System.out.println("Conectando a: " + url + " con usuario: " + dbUser + " y contraseña: " + dbPassword);
//        dataSource.setUrl(url);
//        dataSource.setUsername(dbUser);
//        dataSource.setPassword(dbPassword);
//
//        return dataSource;
//    }
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.tallerwebi.dominio.model.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory(dataSource()).getObject());
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        return properties;
    }
}
