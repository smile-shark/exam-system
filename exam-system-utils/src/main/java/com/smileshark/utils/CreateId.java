package com.smileshark.utils;

import java.util.UUID;

public class CreateId {
    public static String createId(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(createId());
    }
}
