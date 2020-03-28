package com.confuture.controller;

import com.confuture.service.LoginUserService;
import com.confuture.util.JsonResult;
import com.confuture.util.MyUtil;
import com.confuture.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserLoginController {

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/api/v1/sign/in")
    public JsonResult userSignin(@RequestParam String phone, @RequestParam String password) {

        Long user_id = loginUserService.userLoginVerify(phone, password);
        if (user_id == null) {
            return JsonResult.fail("401", "verify error");
        } else {
            String userToken = MyUtil.generateRandomString(50);
            redisService.setString(userToken,user_id.toString());
            Map<String, String> result = new HashMap<>();
            result.put(phone.toString(), userToken);
            return JsonResult.ok(result);
        }
    }

    @GetMapping("/api/v1/all/users")
    public JsonResult queryAllUsers(){
        List allUsers = loginUserService.queryAllUsers();
        return JsonResult.ok(allUsers);
    }

}
