package com.dsys.datacollectiondispatcher.service;

import com.dsys.datacollectiondispatcher.controller.DistpatchingController;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class MessageService {

    ConnectionFactory factory = new ConnectionFactory();
    DistpatchingController dispatcher;

    public MessageService(DistpatchingController dispatcher) {
        this.dispatcher = dispatcher;
    }



    public  void sendMessage(String to, String message, String customer_id) throws Exception {
        factory.setHost("localhost");
        factory.setPort(30003);
        message = customer_id + " " + message;
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare("spring_app", "direct");

            channel.basicPublish("spring_app", to, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + to + "':'" + message + "'");
        }
    }

    public void listen(String[] argv)  throws IOException, TimeoutException {
        factory.setHost("localhost");
        factory.setPort(30003);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("spring_app", "direct");
        String queueName = channel.queueDeclare().getQueue();

        System.out.println(" [x] Dispatcher listening to  '" + queueName + "'");
        for (String bindingKey : argv) {
            channel.queueBind(queueName, "spring_app", bindingKey);
        }

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            try {
                dispatcher.dispatch(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
