package com.example.springboottest.controller;

import com.example.springboottest.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.example.springboottest.controller
 * @Author: HeXiaoBo
 * @CreateDate: 2019/9/4 16:27
 * @Description:
 **/
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * redis查询测试
     * @return
     */
    @RequestMapping("/query")
    public Map<String, Object> queryByKey(String key){
        Object o = redisTemplate.opsForValue().get(key);
        Map<String,Object> map = new HashMap<>();
        map.put(key, o);
        return map;
    }

    @RequestMapping("/addString")
    public String add(){
        User user = new User();
        user.setName("hxb");
        user.setAge(23);
        redisTemplate.opsForValue().set("userTest", user);
        return "success";
    }
}
