package com.myself.mall.thirdparty;

import com.aliyun.oss.OSS;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
class MallThirdpartyApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    OSS oss;

    @Test
    public void ossTest() throws FileNotFoundException {
        InputStream in =new FileInputStream("D:\\documents\\picture\\20210624170425.png");

        oss.putObject("mall-ty","sunway.png",in);

        oss.shutdown();
        System.out.println("上传完成");
    }
}
