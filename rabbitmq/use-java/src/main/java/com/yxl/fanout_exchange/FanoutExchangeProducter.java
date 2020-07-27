package com.yxl.fanout_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: fanout Exchange Producter
 * @author: yexianliang
 * @create: 2020-07-24 16:53
 **/
public class FanoutExchangeProducter {
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
        String exchangeName = "yxl.fanoutExchange";

        //定义routingKey
        String routingKey = "yxl.fanoutExchange.key";

        //消息体内容
        for (int i = 1; i <= 5; i++) {
            String message = "hello fanout exchage " + i;
            channel.basicPublish(exchangeName, routingKey + i, null, message.getBytes());
        }
    }
}
