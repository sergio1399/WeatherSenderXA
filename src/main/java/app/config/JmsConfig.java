package app.config;

import com.atomikos.jms.AtomikosConnectionFactoryBean;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQXAConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "app.components" })
@EnableTransactionManagement
public class JmsConfig {

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
        connectionFactoryBean.setUniqueResourceName("weathertopic");
        connectionFactoryBean.setLocalTransactionMode(false);
        connectionFactoryBean.setMaxPoolSize(100);
        connectionFactoryBean.setXaConnectionFactory(connectionFactory());
        return connectionFactoryBean;
    }

    @Bean
    public ActiveMQTopic weatherTopic(){
        return new ActiveMQTopic("weathertopic");
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setConnectionFactory(connectionFactoryBean());
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setDefaultDestination(weatherTopic());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }
}
