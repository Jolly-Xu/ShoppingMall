package com.xujialin.service.impl;

import com.alibaba.fastjson.JSON;
import com.xujialin.Utils.UUIDGenerator;
import com.xujialin.entity.Orderinfo;
import com.xujialin.mapper.OrderinfoMapper;
import com.xujialin.service.OrderinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.utils.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XujialinDashuaige
 * @since 2021-09-12
 */
@Slf4j
@Service
public class OrderinfoServiceImpl extends ServiceImpl<OrderinfoMapper, Orderinfo> implements OrderinfoService {


    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = "ORDER_QUEUE")
    @Override
    public void CreateOrder(Message message) {
        String s =new String(message.getBody());
        Map map = JSON.parseObject(s, Map.class);
        if (CreateOrderToMysql(map)){
            log.info("订单创建成功---->时间{}",LocalDateTime.now());
        }
        else{
            log.info("订单创建失败---->{}",LocalDateTime.now());
        }
    }

    @Transactional
    public Boolean CreateOrderToMysql(Map<String,String> map){
        String id =  map.get("id");
        LocalDateTime time = LocalDateTime.now();
        String OrderId = UUIDGenerator.GeneratorOrderKey();
        Orderinfo orderinfo = new Orderinfo(OrderId,id, BigDecimal.valueOf(22.50),time,time,null,1,"001",null,null,null,null,null);
        try{
            this.save(orderinfo);
            if (!CreateOrderToRedis(OrderId)){
                throw new RuntimeException();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            log.info("Mysql创建订单失败");
            throw new RuntimeException();
        }
        return true;
    }

    public Boolean CreateOrderToRedis(String id){

        try{
            redisTemplate.opsForValue().set(id,"1");
            redisTemplate.expire(id,60, TimeUnit.SECONDS);
        }catch (Exception e)
        {
            e.printStackTrace();
            log.info("订单录入redis失败---->{}",LocalDateTime.now());
            return false;
        }
        return true;
    }
}
