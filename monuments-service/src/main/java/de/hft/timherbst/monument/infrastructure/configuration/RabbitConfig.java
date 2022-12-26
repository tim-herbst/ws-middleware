package de.hft.timherbst.monument.infrastructure.configuration;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableRabbit
@Configuration
class RabbitConfig implements RabbitListenerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public DirectRabbitListenerContainerFactory listenerContainerFactory() {
        DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory =
                new DirectRabbitListenerContainerFactory();
        directRabbitListenerContainerFactory.setConsumersPerQueue(1);
        directRabbitListenerContainerFactory.setDefaultRequeueRejected(true);
        directRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        directRabbitListenerContainerFactory.setMessageConverter(messageConverter());
        directRabbitListenerContainerFactory.setConnectionFactory(connectionFactory());
        return directRabbitListenerContainerFactory;
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setPublisherReturns(true);
        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setRequestedHeartBeat(Integer.MAX_VALUE);
        return cachingConnectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setContainerFactory(listenerContainerFactory());
    }
}
