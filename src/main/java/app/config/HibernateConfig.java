package app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:hibconfig.xml"})
public class HibernateConfig {
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource());
//        sessionFactoryBean.setPackagesToScan("app.domain.entity");
//        sessionFactoryBean.setHibernateProperties(hibernateProps());
//        return sessionFactoryBean;
//    }
//
//    @Bean
//    public DriverManagerDataSource dataSource(){
//        Properties props = hibernateProps();
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(props.getProperty("hibernate.connection.driver_class"));
//        dataSource.setUrl("hibernate.connection.url");
//        dataSource.setUsername("hibernate.connection.username");
//        dataSource.setPassword("hibernate.connection.password");
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager hibernateTransactionManager() {
//        HibernateTransactionManager transactionManager
//                = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory().getObject());
//        return transactionManager;
//    }
//
//    private final Properties hibernateProps() {
//        return new org.hibernate.cfg.Configuration().configure().getProperties();
//    }
}
