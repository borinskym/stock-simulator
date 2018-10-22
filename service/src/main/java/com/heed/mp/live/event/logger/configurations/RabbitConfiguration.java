package com.heed.mp.live.event.logger.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfiguration {

    @Value("${message-queue.host}")
    private String hostname;

    @Value("${message-queue.userName}")
    private String username;

    @Value("${message-queue.password}")
    private String password;

    @Value("${message-queue.virtualHost}")
    private String virtualHost;

    @Value("${message-queue.exchanges.triggersExchangeName}")
    private String triggersExchangeName;

    @Value("${message-queue.names.liveEventLogger}")
    private String liveEventLoggerQueue;


    @Bean
    public RabbitTemplate rabbitTemplate(AbstractConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public RabbitAdmin defaultRabbitAdmin(AbstractConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public AbstractConnectionFactory connectionFactory() {
        AbstractConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(hostname);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean
    public TopicExchange triggersExchange() {
        return new TopicExchange(triggersExchangeName);
    }

    @Bean
    public Queue liveEventLoggerQueue(){
        return new Queue(liveEventLoggerQueue);
    }

    @Bean
    public Binding soccerClockTriggersBinding(Queue liveEventLoggerQueue, TopicExchange triggersExchange) {
        return BindingBuilder.bind(liveEventLoggerQueue).to(triggersExchange)
                .with("soccer.goal.sologoal");
    }
}
