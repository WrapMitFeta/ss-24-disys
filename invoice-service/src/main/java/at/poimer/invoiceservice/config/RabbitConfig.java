package at.poimer.invoiceservice.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

    /**
     * Routing key for the queue to start invoice generation
     */

    public static final String ROUTING_KEY = "dispatch";

    /**
     * The direct exchange to post a message to the invoice generation queue
     *
     * @return The direct exchange used for posting the message to the queue
     */
    @Bean
    public DirectExchange direct() {
        return new DirectExchange("spring_app");
    }
}
