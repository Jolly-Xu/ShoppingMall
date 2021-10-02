package com.xujialin.Utils;

import java.util.UUID;

/**
 * @author XuJiaLin
 * @date 2021/9/12 14:56
 */
public class UUIDGenerator {
    public static String Generator(){
        return UUID.randomUUID().toString().substring(0,25).replace("-","");
    }

    public static String GeneratorOrderKey(){
        return "Order-"+UUID.randomUUID().toString().substring(0,25).replace("-","");
    }

    /**
     *
     * @param username
     * @return
     */
    public static String GeneratorUserNameInfoKey(String username){
        return "UserName-"+username;
    }

    public static String GeneratorInquireKey(String key){

        return "InquireKey-"+key;
    }

}
