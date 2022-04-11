package com.myself.mall.order;

import com.myself.mall.order.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class MallOrderApplicationTests {

    @Autowired
    AmqpAdmin rabbitAdmin;

    @Autowired
    RabbitTemplate template;

    @Test
    void contextLoads() {
    }

    @Test
    void createExchange(){
        Exchange exchange = new DirectExchange("mall-exchange");
        rabbitAdmin.declareExchange(exchange);
    }

    @Test
    void createQueue(){
        Queue queue = new Queue("mall-queue");
        rabbitAdmin.declareQueue(queue);
    }

    @Test
    void bind(){
        Binding bind = new Binding("mall-queue", Binding.DestinationType.QUEUE,"mall-exchange","mall-queue",null);
        rabbitAdmin.declareBinding(bind);
    }

    @Test
    void sendMessage(){
        OrderEntity order = new OrderEntity();
        order.setOrderSn("11");
        order.setBillContent("123元");
        order.setBillType(1);
        
        template.convertAndSend("mall-queue",order, new CorrelationData(UUID.randomUUID().toString().replace("-","")));
    }
}
