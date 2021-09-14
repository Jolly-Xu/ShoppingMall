package com.xujialin.service;

import com.xujialin.entity.Orderinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.amqp.core.Message;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XujialinDashuaige
 * @since 2021-09-12
 */
public interface OrderinfoService extends IService<Orderinfo> {
    public void CreateOrder(Message message);
}
