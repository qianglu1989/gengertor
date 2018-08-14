package com.generator.autoconfigure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.generator.common.PropertyConfigUtils;

/**
 * Created by liuxueqiang on 2018/3/13.
 */
@Configuration
public class CommonsAutoConfiguration {

    @Bean
    public PropertyConfigUtils propertyConfigUtils() {

        return new PropertyConfigUtils();
    }
}
