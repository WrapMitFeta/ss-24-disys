package com.dsys.pdfgenerator.service;



import com.dsys.pdfgenerator.controller.PDFGeneratorController;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class MessageService {
    private ConnectionFactory factory = new ConnectionFactory();
    private PDFGeneratorController controller;

    public MessageService(PDFGeneratorController controller) {
        this.controller = controller;
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
                controller.print(message_info);
            } catch (Exception ignored) {
            }
        };
        System.out.println(" [x] PDF Generator listening to  '" + queueName + "'");
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
