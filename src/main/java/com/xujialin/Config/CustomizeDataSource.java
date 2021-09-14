package com.xujialin.Config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author XuJiaLin
 * @date 2021/9/12 13:07
 */
@Configuration
public class CustomizeDataSource {

    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource dataSource(){
        return new DruidDataSource();
    }
}
