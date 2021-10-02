package com.xujialin;

import com.xujialin.Utils.UUIDGenerator;
import com.xujialin.service.GoodsinfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

@Slf4j
@SpringBootTest
class ShoppingMallApplicationTests {

    @Autowired
    private GoodsinfoService goodsinfoService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("adhuew154879jiawfw",100);
    }

    //测试uuid生成器
    @Test
    void test01(){
        String generator = UUIDGenerator.Generator();
        System.out.println(generator);
    }

    /**
     * 测试mysql的减库存
     */
    @Test
    void test02(){
        Boolean a = goodsinfoService.UpdateStore("adhuew154879jiawfw");
        System.out.println(a);
    }


    @Test
    void test03(){
        Boolean adwadwad = redisTemplate.delete("adwadwad");
        System.out.println(adwadwad);
    }

    /**
     * 测试mybatis结果封装成Map
     */
    @Test
    void test04(){
        Map<String, Map<String, Object>> map = goodsinfoService.InitStoreCountToRedis();
        for (Map<String, Object> value : map.values()) {
            log.info("{}---{}",value.get("id"),value.get("stockcount"));
        }
    }
}
