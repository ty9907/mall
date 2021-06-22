package com.myself.mall.order.dao;

import com.myself.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 22:11:31
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
