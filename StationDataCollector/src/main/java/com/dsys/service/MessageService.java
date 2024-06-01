package com.dsys.service;

import com.dsys.controller.CollectionController;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class MessageService {
    private ConnectionFactory factory = new ConnectionFactory();


    public boolean sendMessage(String to, String message, String customer_id) throws Exception {
        factory.setHost("localhost");
        factory.setPort(30003);
        message = customer_id + " " + message;
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare("spring_app", "direct");


            channel.basicPublish("spring_app", to, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + to + "':'" + message + "'");
        }
        return true;
    }

    public void listen(String[] argv)  throws IOException, TimeoutException {
        factory.setHost("localhost");
        factory.setPort(30003);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("spring_app", "direct");


        String queueName = channel.queueDeclare().getQueue();
        for (String bindingKey : argv) {
            channel.queueBind(queueName, "spring_app", bindingKey);
        }

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String[] message_info = message.split(" ");
            System.out.println(" [x] Received '" + message + "'");
            try {
              CollectionController.collect(message_info[0], message_info[1]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        System.out.println(" [x] Station Data Collector listening to  '" + queueName + "'");
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

}
