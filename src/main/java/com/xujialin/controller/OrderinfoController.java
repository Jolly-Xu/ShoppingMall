package com.xujialin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xujialin.CommonReturnResult.ReturnResult;
import com.xujialin.Utils.UUIDGenerator;
import com.xujialin.entity.Goodsinfo;
import com.xujialin.service.GoodsinfoService;
import com.xujialin.service.OrderinfoService;
import com.xujialin.service.impl.OrderinfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XujialinDashuaige
 * @since 2021-09-12
 */

@Slf4j
@RestController
@RequestMapping("/orderinfo")
public class OrderinfoController {

    @Autowired
    private GoodsinfoService goodsinfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderinfoServiceImpl orderinfoService;

    private static ConcurrentHashMap<String,Boolean> StoreMap = new ConcurrentHashMap<String,Boolean>();

    /**
     * 下单接口
     * @param map
     * @return
     */
    @PostMapping("/placeorder")
    public ReturnResult PlaceOrder(@RequestBody Map<String,String> map){
        //判断库存
        String id = map.get("goodsid");
        if(StoreMap.containsKey(id)){
            return new ReturnResult(false);
        }
        //先查redis
        Long StoreCount = redisTemplate.opsForValue().decrement(id);
        if (StoreCount >= 0)
        {
            String OrderId = UUIDGenerator.GeneratorOrderKey();
            try{
                goodsinfoService.UpdateStore(id);
                map.put("OrderId", OrderId);
                rabbitTemplate.convertAndSend("ORDER_EXCHANGE","ORDER_KEY",map);
            }catch (Exception e){
                StoreMap.remove(id);
                redisTemplate.opsForValue().increment(id);
            }
            return new ReturnResult("200",true,OrderId);
        }
        else
        {
            StoreMap.put(id,true);
            redisTemplate.opsForValue().increment(id);
            return new ReturnResult(false);
        }
    }

    @PostMapping("/buy")
    public ReturnResult Buy(@RequestBody Map<String,String> map){
        String orderId = map.get("orderid");
        if(orderinfoService.BuyToMysql(orderId)){
            log.info("购买成功");
            return new ReturnResult(true);
        }
        return new ReturnResult(false);
    }


}
