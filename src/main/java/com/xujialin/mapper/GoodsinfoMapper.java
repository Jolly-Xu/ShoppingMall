package com.xujialin.mapper;

import com.xujialin.entity.Goodsinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XujialinDashuaige
 * @since 2021-09-12
 */
@Mapper
public interface GoodsinfoMapper extends BaseMapper<Goodsinfo> {

    Integer UpdateStoreByMysql(String id);

    Integer SelectStoreByMysql(String id);
}
