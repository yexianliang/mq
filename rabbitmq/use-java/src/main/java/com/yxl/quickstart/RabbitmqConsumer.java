package com.yxl.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitmqConsumer {
    // 队列名称
    private final static String QUEUE_NAME = "helloMQ";

    public static void main(String[] argv) throws Exception {

        // 打开连接和创建频道，与发送端一样
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
         * @param queue 队列名
         * @param durable 是否持久化，队列的声明默认是存放到内存中的，如果需要持久化需要设置为 true 持久化会保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
         * @param exclusive 是否为独占队列 如果是独占队列，当关闭连接时，改队列会自动删除；
         * @param autoDelete 是否自动删除，当最后一个消费者断开连接之后队列是否自动删除，
         * @param arguments 队列的其他属性
         * @return a declaration-confirm method to indicate the queue was successfully declared
         * @throws java.io.IOException if an error is encountered
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //创建消费者
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
