package com.condingapi.security;

import com.alibaba.druid.pool.DruidDataSource;
import com.codingapi.security.proxy.db.DataSourceProxy;
import com.codingapi.security.proxy.utils.SecurityConfig;
import com.codingapi.security.proxy.utils.SecurityConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * create by lorne on 2018/1/1
 */
@Configuration
public class DataSourceProxyConfiguration {

    public void reloadDataSource() {
        Map<String, SecurityConfig> securityConfigMap = SecurityConfigUtils.getInstance().getSecurityConfigs();
        for (String name : securityConfigMap.keySet()) {
            SecurityConfig securityConfig = securityConfigMap.get(name);
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(securityConfig.getDbUrl());
            dataSource.setUsername(securityConfig.getDbUsername());//用户名
            dataSource.setPassword(securityConfig.getDbPassword());//密码
            dataSource.setInitialSize(3);
            dataSource.setMaxActive(5);
            dataSource.setMinIdle(0);
            dataSource.setMaxWait(60000);
            dataSource.setValidationQuery("SELECT 1");
            dataSource.setTestOnBorrow(false);
            dataSource.setTestWhileIdle(true);
            dataSource.setPoolPreparedStatements(false);
            DataSourceProxy.addDataSource(name, dataSource);
        }
    }


    @Bean
    public DataSourceProxy dataSource() {
        DataSourceProxy dataSourceProxy = new DataSourceProxy();
        reloadDataSource();
        return dataSourceProxy;
    }
}
