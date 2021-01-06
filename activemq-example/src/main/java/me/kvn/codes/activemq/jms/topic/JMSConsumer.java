package me.kvn.codes.activemq.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer {
    public static void main(String[] args) throws JMSException {
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    consumer();
                }
            }).start();
        }
    }

    public static void consumer() {
        //消息目的地
        String destinationurl = "tcp://localhost:61616";
        //队列的名称
        String TopicName = "jms-topic-1";
        Connection connection = null;
        try {
            //创建连接工厂，通过连接工厂创建连接
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(destinationurl);
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话：
            /**
             * 1、通过会话创建队列对象
             * 2、通过会话创建生成者对象
             * 3、通过会话创建消息对象
             */
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建队列对象，创建时设置队列名称
            Topic topic = session.createTopic(TopicName);//Topic继承自Destionation
            //根据队列对象创建消费者
            MessageConsumer consumer = session.createConsumer(topic);

            //接受消息
            connection.start();
            while (true) {
                TextMessage message = (TextMessage) consumer.receive();
                System.out.println(Thread.currentThread().getName() + " - 接受到消息:" + message.getText());
            }
        } catch (JMSException je) {
            je.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    //关闭连接
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
