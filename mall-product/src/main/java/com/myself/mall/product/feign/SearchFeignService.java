package com.myself.mall.product.feign;


import com.myself.common.to.es.SkuEsModel;
import com.myself.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>Title: SearchFeignService</p>
 * Description：远程上架商品
 * date：2020/6/8 21:42
 */
//@FeignClient("mall-search")
public interface SearchFeignService {

//	@PostMapping("/search/save/product")
//	R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
