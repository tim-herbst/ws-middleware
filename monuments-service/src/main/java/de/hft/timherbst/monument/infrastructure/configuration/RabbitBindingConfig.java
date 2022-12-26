package de.hft.timherbst.monument.infrastructure.configuration;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
class RabbitBindingConfig {

    private final RabbitDeclarableConfigProperties declarableConfig;

    @Bean
    public Declarables topicBinding() {
        final RabbitDeclarableConfigProperties.Monument monumentConfig = declarableConfig.getMonument();
        final TopicExchange monumentExchange = new TopicExchange(monumentConfig.getExchange());
        final Map<String, Object> args = new HashMap<>();
        args.put("x-single-active-consumer", true);
        final Queue monumentQueue = new Queue(monumentConfig.getQueue(), true, false, false, args);
        return new Declarables(
                monumentQueue,
                BindingBuilder.bind(monumentQueue).to(monumentExchange).with(monumentConfig.getRoutingKey()));
    }
}
