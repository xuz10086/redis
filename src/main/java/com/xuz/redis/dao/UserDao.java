package com.xuz.redis.dao;

import com.xuz.redis.pojo.User;

public interface UserDao {

    User select(Integer id);
}
