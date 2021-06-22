package com.myself.mall.coupon.dao;

import com.myself.mall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 21:53:38
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
