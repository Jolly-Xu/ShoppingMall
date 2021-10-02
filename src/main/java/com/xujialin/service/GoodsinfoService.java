package com.xujialin.service;

import com.xujialin.entity.Goodsinfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XujialinDashuaige
 * @since 2021-09-12
 */
public interface GoodsinfoService extends IService<Goodsinfo> {
    public Boolean UpdateStore(String id);

    Map<String, Map<String, Object>> InitStoreCountToRedis();

    List<Goodsinfo> getAllGoodsInfoByMysql(String start,String count);
}
