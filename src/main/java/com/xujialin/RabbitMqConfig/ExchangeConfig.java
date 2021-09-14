package com.xujialin.RabbitMqConfig;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XuJiaLin
 * @date 2021/9/12 22:46
 */

@Configuration
public class ExchangeConfig {
    private final static String ORDER_QUEUE = "ORDER_QUEUE";
    private final static String ORDER_KEY = "ORDER_KEY";
    private final static String ORDER_EXCHANGE = "ORDER_EXCHANGE";

    /**
     * 声明订单交换机
     * @return
     */
    @Bean
    public DirectExchange OrderExchange(){
            return new DirectExchange(ORDER_EXCHANGE,false,false);
    }

    /**
     * 声明订单队列
     * @return
     */
    @Bean
    public Queue OrderQueue(){
        return QueueBuilder.nonDurable(ORDER_QUEUE).build();
    }

    @Bean
    public Binding OrderBinding(@Qualifier("OrderExchange") DirectExchange exchange,
                                @Qualifier("OrderQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_KEY);
    }
}
