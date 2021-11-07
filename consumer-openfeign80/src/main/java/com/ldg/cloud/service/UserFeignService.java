package com.ldg.cloud.service;

import com.ldg.cloud.pojo.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFeignService implements UserFeign{
    @Override
    public List<User> getAll() {
        User user = new User();
        user.setName("服务器Down机了");
        List<User> list=new ArrayList<>();
        list.add(user);
        return list;
    }
}
