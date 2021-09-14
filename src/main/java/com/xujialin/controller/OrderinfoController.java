package com.xujialin.controller;


import com.xujialin.CommonReturnResult.ReturnResult;
import com.xujialin.entity.Goodsinfo;
import com.xujialin.service.GoodsinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
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

    private static ConcurrentHashMap<String,Boolean> StoreMap = new ConcurrentHashMap<String,Boolean>();


    /**
     * 下单接口
     * @param id
     * @return
     */
    @PostMapping("/placeorder")
    public ReturnResult PlaceOrder(String id,String userid,String address,String storeid){
        //判断库存
        if(StoreMap.containsKey(id)){
            return new ReturnResult(false);
        }
        //先查redis
        Long StoreCount = redisTemplate.opsForValue().decrement(id);
        if (StoreCount >= 0)
        {
            try{
                goodsinfoService.UpdateStore(id);
                Map<String,String> map = new HashMap<>();
                map.put("id",id);
                rabbitTemplate.convertAndSend("ORDER_EXCHANGE","ORDER_KEY",map);
            }catch (Exception e){
                StoreMap.remove(id);
                redisTemplate.opsForValue().decrement(id);
            }
            return new ReturnResult(true);
        }

        else
        {
            StoreMap.put(id,true);
            redisTemplate.opsForValue().increment(id);
            return new ReturnResult(false);
        }
    }

    @GetMapping("/test")
    public ReturnResult test(String id){
        //redisTemplate.opsForValue().decrement(id);
        return new ReturnResult(false);
    }
}
