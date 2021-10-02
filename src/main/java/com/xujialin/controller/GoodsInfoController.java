package com.xujialin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xujialin.CommonReturnResult.ReturnResult;
import com.xujialin.Utils.UUIDGenerator;
import com.xujialin.entity.Goodsinfo;
import com.xujialin.service.GoodsinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
@RequestMapping("/goodsinfo")
public class GoodsInfoController {

    @Autowired
    private GoodsinfoService goodsinfoService;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostConstruct
    public void InitStoreCountToRedis(){
        Map<String, Map<String, Object>> map = goodsinfoService.InitStoreCountToRedis();
        for (Map<String, Object> value : map.values()) {
            try{
                redisTemplate.opsForValue().set(value.get("id"),value.get("stockcount"));
            }catch (Exception e)
            {
                e.printStackTrace();
                log.info("启动时,将数据载入进redis出错");
            }

        }
    }

    /**
     * 首页获取全部商品信息
     * @param start
     * @param count
     * @return
     */
    @GetMapping("/getAllGoodsInfo")
    public ReturnResult getAllGoodsInfo(String start,String count){
        String key = UUIDGenerator.GeneratorInquireKey(start+count);
        //先查询redis数据库
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null)
        {
            try{
                List<Goodsinfo> allGoodsInfoByMysql = goodsinfoService.getAllGoodsInfoByMysql(start, count);
                redisTemplate.opsForValue().set(key,allGoodsInfoByMysql);
                redisTemplate.expire(key,1, TimeUnit.DAYS);
                log.info("{}",allGoodsInfoByMysql);
                return new ReturnResult("200",true, allGoodsInfoByMysql);
            }catch (Exception e)
            {
                e.printStackTrace();
                return new ReturnResult("404",false,"出错了");
            }
        }
        return new ReturnResult("200",true,o);
    }

    @GetMapping("/getgoodsinfoById")
    public ReturnResult getgoodsinfoById(String Id){
        //先查redis
        String key = UUIDGenerator.GeneratorInquireKey("goodId-" + Id);
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) {
            try {
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("id", Id);
                Goodsinfo one = goodsinfoService.getOne(wrapper);
                log.info("{}", one);
                one.setCreatetime(null);
                one.setUpdatatime(null);
                redisTemplate.opsForValue().set(key,one);
                redisTemplate.expire(key,1,TimeUnit.DAYS);
                return new ReturnResult("200", true, one);
            }catch (Exception e){
                e.printStackTrace();
                return new ReturnResult("404",false);
            }
        }
        return new ReturnResult("200", true, o);
    }
}
