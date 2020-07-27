package com.yxl.topic_exchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: topic Exchange Consumer
 * @author: yexianliang
 * @create: 2020-07-24 17:01
 **/
public class TopicExchangeConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 打开连接和创建频道，与发送端一样
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "yxl.topicExchange";
        String exchangeType = "topic";
        String queueName = "yxl.topicqueue";
        String routingKey = "yxl.topicExchange.key1";

        /**
         * 声明一个交换器
         * exchangeName：交换器的名字
         * exchangeType：交换器的类型 常见的有topic、topic、topic等
         * durable：设置是否持久化。为true时表示持久化，反之非持久化。持久化可以将交换器存入磁盘，在服务器重启的时候不会丢失相关信息；
         * autodelete：设置是否自动删除。所有与该交换器绑定的队列解绑后，是否自动删除；
         * arguments：其他一些参数
         */
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);

        channel.queueDeclare(queueName, true, false, false, null);

        /**
         * 队列和交换器绑定
         */
        channel.queueBind(queueName, exchangeName, routingKey);

        //创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[topicExchangeConsumer] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
