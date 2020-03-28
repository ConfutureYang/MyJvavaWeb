package com.confuture.dao;

import com.confuture.models.LoginUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


public interface LoginUserDao {

    LoginUser queryLoginUserByPhone(String phone);

    List queryAllLoginUsers();

    void insertLoginUser(LoginUser loginUser);

    Long userLoginVerify(@Param("phone") String phone, @Param("password") String password);

}
