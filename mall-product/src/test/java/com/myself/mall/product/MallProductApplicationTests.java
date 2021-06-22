package com.myself.mall.product;

import com.myself.mall.product.entity.BrandEntity;
import com.myself.mall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication(scanBasePackageClasses = MallProductApplication.class)
class MallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    public void contextLoads() {

        BrandEntity brand=new BrandEntity();
        brand.setDescript("商品描述");
        brand.setLogo("logo");
        brandService.save(brand);
    }

}
