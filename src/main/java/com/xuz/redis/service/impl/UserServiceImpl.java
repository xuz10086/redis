package com.xuz.redis.service.impl;

import com.xuz.redis.dao.UserDao;
import com.xuz.redis.pojo.User;
import com.xuz.redis.service.UserService;
import com.xuz.redis.util.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
//@CacheConfig(cacheNames = "user") // 统一写到类上，后面方法可以不用写cacheNames，只需要注明key
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 第一次查询，访问数据库，后续直接从缓存中获取
     *  
     * @author xuz
     * @date 2019/4/12 3:49 PM
     * @param []
     * @return com.xuz.redis.util.JSONObject
     */
    @Cacheable(key="'user'+#id")
    @Override
    public JSONObject selectUser(Integer id) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User userList = userDao.select(id);
        return JSONObject.build(200, "ok", userList);
    }

    /**
     * 更新缓存
     *  
     * @author xuz
     * @date 2019/4/12 3:25 PM
     * @param [id]
     * @return com.xuz.redis.util.JSONObject
     */
    @CachePut(key = "'user'+#id")
    @Override
    public JSONObject updateRedis(Integer id) {
        return JSONObject.oK("更新");
    }

    /**
     * 删除缓存
     *
     * @author xuz
     * @date 2019/4/12 4:42 PM
     * @param [id]
     * @return void
     */
    @CacheEvict(key = "'user'+#id")
    @Override
    public void deleteRedis(Integer id) {
        System.out.print("删除缓存");
    }

    /**
     * RedisConfig，插入缓存
     *  
     * @author xuz
     * @date 2019/4/15 9:50 AM
     * @param [id]
     * @return com.xuz.redis.util.JSONObject
     */
    @Override
    public JSONObject selectUser2(Integer id) {
        String key = "user_"+id;
        ValueOperations<String,User> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            User user = operations.get(key);
            System.out.println("从缓存中获取用户：" + user.toString());
            return JSONObject.build(200, "ok", user);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = userDao.select(id);
        //插入缓存
        operations.set(key, user, 10, TimeUnit.HOURS);
        System.out.println(("用户插入缓存：" + user.toString()));

        return JSONObject.build(200, "ok", user);
    }

    @Override
    public JSONObject updateUser(User user) {
        String key = "user_" + user.getId();
        ValueOperations operations = redisTemplate.opsForValue();
        Boolean hasKey = redisTemplate.hasKey(key);

        if (hasKey) {
            operations.set(key, user, 10, TimeUnit.HOURS);
        }

        // 更新数据库数据

        return JSONObject.build(200, "ok", user);
    }

    @Override
    public JSONObject deleteUser(Integer id) {
        String key = "user_" + id;
        ValueOperations operations = redisTemplate.opsForValue();
        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);
        }

        // 数据库删除数据

        return JSONObject.build(200, "OK", null);
    }


}
