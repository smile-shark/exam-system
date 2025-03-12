package com.smileshark.utils;

import java.util.UUID;

public class CreateId {
    public static String createId(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
