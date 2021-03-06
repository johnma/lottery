package com.iware.lottery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by johnma on 2016/11/12.
 */
@Configuration
public class RedisTemplateConfig {

    @Bean
     JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jpc =  new JedisPoolConfig();
        /*
                jpc.setMaxIdle();
                jpc.setMaxWaitMillis();
                jpc.setMaxTotal();
                */

        return jpc;
    }

    @Bean
     JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jcf = new JedisConnectionFactory();
        //jcf.setHostName("127.0.0.1");
        jcf.setHostName("120.25.223.57");
        jcf.setPort(6379);
        jcf.setPassword("123");

        jcf.setPoolConfig(jedisPoolConfig());
        jcf.afterPropertiesSet();

        return jcf;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        //一般redis的设置会是这样的
        final RedisTemplate<String, Object> template =  new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

}
