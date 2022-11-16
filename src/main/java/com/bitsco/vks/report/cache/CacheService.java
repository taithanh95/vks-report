package com.bitsco.vks.report.cache;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.report.model.Spp;
import com.bitsco.vks.report.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: truongnq
 * @date: 06-May-19 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CacheService {
    @Autowired
    RedisTemplate<String, User> userRedis;

    @Autowired
    RedisTemplate<String, Spp> sppRedis;


    @Autowired
    HttpServletRequest request;

    public User getUserFromCache(String username) {
        final ValueOperations<String, User> opsForValue = userRedis.opsForValue();
        String key = Constant.TABLE.USERS + Constant.DASH + username.trim().toLowerCase();
        if (userRedis.hasKey(key))
            return opsForValue.get(key);
        else return null;
    }

    public Spp getSppFromCache(String sppCode) {
        final ValueOperations<String, Spp> opsForValue = sppRedis.opsForValue();
        String key = Constant.TABLE.LST_SPP + Constant.DASH + sppCode.trim().toLowerCase();
        if (sppRedis.hasKey(key))
            return opsForValue.get(key);
        else return null;
    }

    public String getHeaderValue(String headerName) {
        try {
            return request.getHeader(headerName);
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsernameFromHeader() {
        return getHeaderValue(Constant.KEY.USERNAME);
    }

}
