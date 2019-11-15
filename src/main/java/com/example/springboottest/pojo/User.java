package com.example.springboottest.pojo;

import java.io.Serializable;

/**
 * @Package: com.example.springboottest.pojo
 * @Author: HeXiaoBo
 * @CreateDate: 2019/9/4 17:04
 * @Description:
 **/
public class User implements Serializable {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
