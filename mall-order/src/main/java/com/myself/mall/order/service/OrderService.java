package com.myself.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.order.entity.OrderEntity;
import com.myself.mall.order.vo.OrderConfirmVo;
import com.myself.mall.order.vo.OrderSubmitVo;
import com.myself.mall.order.vo.SubmitOrderResponseVo;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 22:11:31
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo);

    void closeOrder(OrderEntity entity);

    OrderEntity getOrderByOrderSn(String orderSn);
}

