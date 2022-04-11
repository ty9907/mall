package com.myself.mall.order.config;

import com.myself.mall.order.entity.OrderEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyMQConfig {

    @Value("${myRabbitmq.MQConfig.eventExchange}")
    private String eventExchange;

    @Value("${myRabbitmq.MQConfig.routingKey}")
    private String routingKey;  //死信路由

    @Value("${myRabbitmq.MQConfig.delayQueue}")
    private String delayQueue; //延迟队列

    @Value("${myRabbitmq.MQConfig.queues}")
    private String queues;  //死信队列

    @Value("${myRabbitmq.MQConfig.ttl}")
    private Long ttl;

    @Value("${myRabbitmq.MQConfig.createOrder}")
    private String createRoutingKey;  //延迟路由

//    @Value("${myRabbitmq.MQConfig.releaseOther}")
//    private String releaseOther;
//
//    @Value("${myRabbitmq.MQConfig.releaseOtherKey}")
//    private String releaseOtherKey;

    @RabbitListener(queues={"order.release.order.queue"})
    public void listen(OrderEntity order, Message message, Channel channel) throws IOException {

        System.out.println("收到延时消息："+order.getOrderSn()+"。时间："+ LocalDateTime.now().format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag,false);
    }

    @Bean
    public Queue orderDelayQueue(){
        Map<String ,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", eventExchange);
        arguments.put("x-dead-letter-routing-key", routingKey);
        arguments.put("x-message-ttl", ttl);
//        return QueueBuilder.durable(delayQueue).exclusive().withArguments(arguments).build();

        return new Queue(delayQueue, true, false, false, arguments);
    }

    @Bean
    public Queue orderReleaseOrderQueue(){
        Queue queue = new Queue(queues, true, false, false);
        return queue;
    }

    @Bean
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(eventExchange).durable(true).build();
//        return new TopicExchange(eventExchange,true,false,null);
    }

    @Bean
    public Binding orderCreateOrderBinding(){
        return new Binding(delayQueue, Binding.DestinationType.QUEUE, eventExchange, createRoutingKey, null);
    }

    @Bean
    public Binding orderReleaseOrderBinding(){
        return new Binding(queues, Binding.DestinationType.QUEUE, eventExchange, routingKey, null);
    }

//    /**
//     * 订单释放直接和库存释放进行绑定
//     */
//    @Bean
//    public Binding orderReleaseOtherBinding(){
//
//        return new Binding(releaseOther, Binding.DestinationType.QUEUE, eventExchange, releaseOtherKey + ".#", null);
//    }
}
