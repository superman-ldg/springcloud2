package com.ldg.cloud.service;

import com.ldg.cloud.dao.UserDao;
import com.ldg.cloud.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAll(){
        return userDao.getAll();
    }
}
