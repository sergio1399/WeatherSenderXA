package app.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import org.apache.activemq.spring.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.SystemException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
@ComponentScan(basePackages = { "app.components" })
@EnableTransactionManagement
public class RootConfig {

    @Bean
    public UserTransactionManager userTransactionManager() throws SystemException {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        userTransactionManager.init();
        return userTransactionManager;
    }

    @Bean
    public UserTransactionImp userTransactionImp() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(1000);
        return userTransactionImp;
    }

    @Bean
    public JtaTransactionManager transactionManager() throws SystemException {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(userTransactionManager());
        jtaTransactionManager.setUserTransaction(userTransactionImp());
        //jtaTransactionManager.setAllowCustomIsolationLevels(true);
        return jtaTransactionManager;
    }

    @Bean
    SpringJtaPlatformAdapter springJtaPlatformAdapter() throws SystemException {
        SpringJtaPlatformAdapter springJtaPlatformAdapter = new SpringJtaPlatformAdapter();
        springJtaPlatformAdapter.setJtaTransactionManager(transactionManager());
        return springJtaPlatformAdapter;
    }


    /*JMS support*/
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public ActiveMQXAConnectionFactory connectionFactory(){
        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory();
        connectionFactory.setUseAsyncSend(true);
        connectionFactory.setAlwaysSessionAsync(true);
        connectionFactory.setStatsEnabled(true);
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        return connectionFactory;
    }

    @Bean
    public AtomikosConnectionFactoryBean connectionFactoryBean(){
        AtomikosConnectionFactoryBean connectionFactoryBean = new AtomikosConnectionFactoryBean();
        connectionFactoryBean.setUniqueResourceName("prospring4");
        connectionFactoryBean.setLocalTransactionMode(false);
        connectionFactoryBean.setMaxPoolSize(100);
        connectionFactoryBean.setXaConnectionFactory(connectionFactory());
        return connectionFactoryBean;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setConnectionFactory(connectionFactoryBean());
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setDefaultDestinationName("prospring4");
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        return jmsTemplate;
    }

}