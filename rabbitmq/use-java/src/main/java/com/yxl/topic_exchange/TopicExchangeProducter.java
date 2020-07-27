package com.yxl.topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: topic Exchange Producter
 * @author: yexianliang
 * @create: 2020-07-24 16:53
 **/
public class TopicExchangeProducter {
    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 创建连接连接到MabbitMQ
         */
        ConnectionFactory factory = new ConnectionFactory();
        //设置MabbitMQ所在主机ip或者主机名
        factory.setHost("localhost");

        //创建一个连接
        Connection connection = factory.newConnection();

        //创建channel
        Channel channel = connection.createChannel();

        //定义交换机名称
        String exchangeName = "yxl.topicExchange";

        //定义RoutingKey
        String routingKey1 = "yxl.topicExchange.key1";
        String routingKey2 = "yxl.topicExchange.key2";
        String routingKey3 = "yxl.topicExchange.key.key3";

        channel.basicPublish(exchangeName, routingKey1, null, "第一条消息".getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, "第二条消息".getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, "第三条消息".getBytes());
    }
}
