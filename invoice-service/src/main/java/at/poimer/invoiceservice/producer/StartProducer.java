package at.poimer.invoiceservice.producer;

import at.poimer.invoiceservice.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.stereotype.Service;

/***
 * Sends a message to the queue to start the invoice generation
 */
@Slf4j
@Service
public class StartProducer {

    private final AmqpTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    public StartProducer(
            AmqpTemplate rabbitTemplate,
            DirectExchange directExchange
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    public void send(int customerId) {
        log.info("Publishing text '{}' with key '{}'", customerId, RabbitConfig.ROUTING_KEY);

        rabbitTemplate.convertAndSend(directExchange.getName(), RabbitConfig.ROUTING_KEY, Integer.toString(customerId));
    }
}
