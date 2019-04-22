package com.xuz.redis.service;

import com.xuz.redis.pojo.User;
import com.xuz.redis.util.JSONObject;
import org.springframework.cache.annotation.CachePut;

import java.util.List;

public interface UserService {

    JSONObject selectUser(Integer id);

    JSONObject updateRedis(Integer id);

    void deleteRedis(Integer id);

    JSONObject selectUser2(Integer id);

    JSONObject updateUser(User user);

    JSONObject deleteUser(Integer id);
}
