package com.myself.mall.order.feign;

import com.myself.mall.order.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * <p>Title: MemberFeignService</p>
 * Description：
 * date：2020/6/30 16:54
 */
@FeignClient("mall-member")
public interface MemberFeignService {

	@GetMapping("/member/memberreceiveaddress/{memberId}/addresses")
	List<MemberAddressVo> getAddress(@PathVariable("memberId") Long memberId);
}
