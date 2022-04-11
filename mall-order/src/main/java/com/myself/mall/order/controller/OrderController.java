package com.myself.mall.order.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

////import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.myself.mall.order.entity.OrderEntity;
import com.myself.mall.order.service.OrderService;
import com.myself.common.utils.PageUtils;
import com.myself.common.utils.R;



/**
 * 订单
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 22:11:31
 */
@RestController
@RequestMapping("order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RabbitTemplate template;

    @Value("${myRabbitmq.MQConfig.eventExchange}")
    private String eventExchange;

    @Value("${myRabbitmq.MQConfig.createOrder}")
    private String routingKey;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:order:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("order:order:info")
    public R info(@PathVariable("id") Long id){
		OrderEntity order = orderService.getById(id);

        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:order:save")
    public R save(@RequestBody OrderEntity order){
		orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("order:order:update")
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("order:order:delete")
    public R delete(@RequestBody Long[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/status/{orderSn}")
    public R getOrderStatus(@PathVariable("orderSn") String orderSn){
        OrderEntity orderEntity = orderService.getOrderByOrderSn(orderSn);

        return R.ok().setData(orderEntity);
    }

    @GetMapping("/test/rabitmq")
    public void rabbitMqTest(){
        OrderEntity order = new OrderEntity();
        order.setOrderSn(UUID.randomUUID().toString().replace("-",""));
        template.convertAndSend(eventExchange,routingKey,order);
    }
}
