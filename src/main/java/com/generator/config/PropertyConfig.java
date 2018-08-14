package com.generator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description: 配置文件适配器
 * @author Jack
 * @date 2017年1月16日 下午2:42:03
 */
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = {"classpath:config/remote-settings.properties"})
public class PropertyConfig extends WebMvcConfigurerAdapter
{
    
}
