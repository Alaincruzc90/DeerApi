package application.conf;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("application.model"),
        @ComponentScan("application.core") })
@PropertySource("classpath:properties/hibernate.properties")
public class HibernateConfiguration {

    //This values are declared in the resources/properties/hibernate.properties file.
    @Value("${database.driver_class_name}")
    private String databaseDriverClassName;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.username}")
    private String databaseUserName;

    @Value("${database.password}")
    private String databasePassword;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSQL;

    @Value("${hibernate.isolation}")
    private String hibernateIsolation;

    @Value("${hibernate.c3p0_acquire_increment}")
    private String c3p0AcquireIncrement;

    @Value("${hibernate.c3p0_idle_test_period}")
    private String c3p0IdleTestPeriod;

    @Value("${hibernate.c3p0_timeout}")
    private String c3p0Timeout;

    @Value("${hibernate.c3p0_max_size}")
    private String c3p0MaxSize;

    @Value("${hibernate.c3p0_min_size}")
    private String c3p0MinSize;

    @Value("${hibernate.c3p0_max_statements}")
    private String c3p0MaxStatements;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseDriverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUserName);
        dataSource.setPassword(databasePassword);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan("application.model");
        sessionFactory.setHibernateProperties(getHibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(getSessionFactory().getObject());
        return txManager;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, hibernateDialect);
        properties.put(AvailableSettings.SHOW_SQL, hibernateShowSQL);
        properties.put(AvailableSettings.ISOLATION, hibernateIsolation);
        properties.put(AvailableSettings.C3P0_ACQUIRE_INCREMENT, c3p0AcquireIncrement);
        properties.put(AvailableSettings.C3P0_IDLE_TEST_PERIOD, c3p0IdleTestPeriod);
        properties.put(AvailableSettings.C3P0_TIMEOUT, c3p0Timeout);
        properties.put(AvailableSettings.C3P0_MAX_SIZE, c3p0MaxSize);
        properties.put(AvailableSettings.C3P0_MIN_SIZE, c3p0MinSize);
        properties.put(AvailableSettings.C3P0_MAX_STATEMENTS, c3p0MaxStatements);
        return properties;
    }

}
