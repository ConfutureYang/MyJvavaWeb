package com.confuture.util;


import java.util.Random;

public class MyUtil {

    public static String generateRandomString(int length){
        String baseString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int randomBound = baseString.length() - 1;
        Random random = new Random();
        StringBuffer randomString = new StringBuffer();
        for (int i=0;i<length;i++){
            int randomPosition = random.nextInt(randomBound);
            randomString.append(baseString.charAt(randomPosition));
        }
        return randomString.toString();
    }

}
