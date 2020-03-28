package com.confuture.service.impl;

import com.confuture.dao.LoginUserDao;
import com.confuture.models.LoginUser;
import com.confuture.service.LoginUserService;
import com.confuture.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private LoginUserDao loginUserDao;

    @Autowired
    private RedisService redisService;

    public void userSignUp(LoginUser loginUser){
        loginUserDao.insertLoginUser(loginUser);
    }

    public Long userLoginVerify(String phone, String password){
        Long user_id = loginUserDao.userLoginVerify(phone, password);
        if (user_id != null){
            return user_id;
        }
        return null;
    }

    public List queryAllUsers(){
        return loginUserDao.queryAllLoginUsers();
    }
}
