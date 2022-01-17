package com.myself.mall.product.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableConfigurationProperties(CacheProperties.class)
@Configuration
@EnableCaching
public class MyCacheConfig {

    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // 设置kv的序列化机制
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        CacheProperties.Redis redisproperties = cacheProperties.getRedis();

        // 设置配置
        if(redisproperties.getTimeToLive() != null){
            config = config.entryTtl(redisproperties.getTimeToLive());
        }
        if(redisproperties.getKeyPrefix() != null){
            config = config.prefixKeysWith(redisproperties.getKeyPrefix());
        }
        if(!redisproperties.isCacheNullValues()){
            config = config.disableCachingNullValues();
        }
        if(!redisproperties.isUseKeyPrefix()){
            config = config.disableKeyPrefix();
        }
        return config;
    }
}
