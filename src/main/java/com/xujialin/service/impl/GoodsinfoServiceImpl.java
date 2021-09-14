package com.xujialin.service.impl;

import com.xujialin.entity.Goodsinfo;
import com.xujialin.mapper.GoodsinfoMapper;
import com.xujialin.service.GoodsinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

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
public class GoodsinfoServiceImpl extends ServiceImpl<GoodsinfoMapper, Goodsinfo> implements GoodsinfoService {

    private static ConcurrentHashMap<String,Boolean> map = new ConcurrentHashMap<String, Boolean>();
    @Override
    @Transactional
    public Boolean UpdateStore(String id) {
        if (map.containsKey(id)){
            return false;
        }
        try{
            Integer flag = this.baseMapper.UpdateStoreByMysql(id);
            if (flag == 1){
                Integer store = this.baseMapper.SelectStoreByMysql(id);
                if (store<0){
                    map.put(id,true);
                    log.info("数据库超卖,回退库存.时间{}",LocalDateTime.now());
                    throw new RuntimeException();
                }
                //log.info("当前库存{}" , store);
            }
        }catch (Exception e)
        {
            log.info("数据库更新数据是出错 时间{}", LocalDateTime.now());
            map.remove(id);
            e.printStackTrace();
        }
        return true;
    }
}
