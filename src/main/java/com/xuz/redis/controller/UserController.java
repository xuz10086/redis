package com.xuz.redis.controller;

import com.xuz.redis.pojo.User;
import com.xuz.redis.service.UserService;
import com.xuz.redis.util.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test/{id}")
    public JSONObject testRedis(@PathVariable Integer id) {
        JSONObject json = userService.selectUser(id);
        return json;
    }

    @GetMapping("/update/{id}")
    public JSONObject updateRedis(@PathVariable Integer id) {
        JSONObject jsonObject = userService.updateRedis(id);
        return jsonObject;
    }

    @GetMapping("/delete/{id}")
    public JSONObject deleteRedis(@PathVariable Integer id) {
        userService.deleteRedis(id);
        return JSONObject.oK("删除缓存");
    }

    @GetMapping("/redisTemplateSave/{id}")
    public JSONObject redisTemplateTestSave(@PathVariable Integer id) {
        JSONObject jsonObject = userService.selectUser2(id);
        return jsonObject;
    }

    @GetMapping("/redisTemplateUpdate")
    public JSONObject redisTemplateTestUpdate(User user) {
        JSONObject jsonObject = userService.updateUser(user);
        return jsonObject;
    }

    @GetMapping("/redisTemplateDelete/{id}")
    public JSONObject redisTemplateTestDelete(@PathVariable Integer id) {
        JSONObject jsonObject = userService.deleteUser(id);
        return jsonObject;
    }
}
