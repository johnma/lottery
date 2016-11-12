package com.iware.lottery.common;

import java.util.UUID;

/**
 * Created by wuhao on 16/11/6.
 * GENERATE vari
 */
public class IdKit {


    public static String getToken(){
        String  uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-","").toString();
        return  uuid;
    }

}
