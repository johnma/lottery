package com.iware.lottery.service;

import com.iware.lottery.Constants;
import com.iware.lottery.model.Token;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by johnma on 2016/11/11.
 */
@Component
public class TokenService {
    private static final Logger logger = LoggerFactory.logger(TokenService.class);

    private RedisTemplate<Long, String> redis;

    @Inject
    public TokenService(RedisTemplate<Long, String> redis){
        this.redis = redis;
    }

/*
    public void setRedis(RedisTemplate redis) {
        this.redis = redis;
        //泛型设置成Long后必须更改对应的序列化方案
        //redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }
*/
    public Token createToken(Long userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        Token tokenModel = new Token(userId, token);
        //存储到redis并设置过期时间
        redis.boundValueOps(userId).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);

        return tokenModel;
    }

    public Token getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new Token(userId, token);
    }

    public boolean checkToken(Token tokenModel) {
        if (tokenModel == null) {
            return false;
        }

        Long userId = tokenModel.getUserId();
        String token = redis.boundValueOps(userId).get();
        if (token == null || !token.equals(tokenModel.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(tokenModel.getUserId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    public void deleteToken(long userId) {
        redis.delete(userId);
    }
}
