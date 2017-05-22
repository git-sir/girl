package com.imooc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by ucs_xiaokailin on 2017/5/15.
 */
@Component
@ConfigurationProperties(prefix = "user")   //这里的前缀要跟yml文件中对应的配置的前缀相同
public class UserProperties {

    private int age;
    private String gender;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserProperties{" +
                "age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
