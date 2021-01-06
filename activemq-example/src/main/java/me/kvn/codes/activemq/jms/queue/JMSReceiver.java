package me.kvn.codes.activemq.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSReceiver {
    public static void main(String[] args) throws JMSException {
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    receiver();
                }
            }).start();
        }
    }

    public static void receiver() {
        //消息目的地
        String destinationurl = "tcp://localhost:61616";
        //队列的名称
        String QueueName = "jms-queue-1";

        Connection connection = null;
        try {
            //创建连接工厂，通过连接工厂创建连接
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(destinationurl);
            connection = connectionFactory.createConnection();
            //打开连接
            connection.start();
            //创建会话：
            /**
             * 1、通过会话创建队列对象
             * 2、通过会话创建生成者对象
             * 3、通过会话创建消息对象
             */
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建队列对象，创建时设置队列名称
            Queue queue = session.createQueue(QueueName);//Queue继承自Destionation
            //根据队列对象创建消费者
            MessageConsumer consumer = session.createConsumer(queue);
            //接受消息

            while (true) {
                TextMessage message = (TextMessage) consumer.receive();
                System.out.println(Thread.currentThread().getName() + " - 接受到消息:" + message.getText());
            }

            //设置监听器
//            consumer.setMessageListener(new MessageListener() {
//                @Override
//                public void onMessage(Message message) {
//                    TextMessage textMessage = (TextMessage) message;
//                    try {
//                        System.out.println("接受到消息:" + textMessage.getText());
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        } catch (JMSException je) {
            je.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    // 关闭连接
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
