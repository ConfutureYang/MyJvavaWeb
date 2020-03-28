package com.confuture.service;

import com.confuture.models.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;


public interface LoginUserService {

    public void userSignUp(LoginUser loginUser);

    public Long userLoginVerify(String phone, String password);

    public List queryAllUsers();

}
