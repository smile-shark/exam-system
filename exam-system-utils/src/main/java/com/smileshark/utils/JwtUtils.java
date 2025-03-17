package com.smileshark.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;

public class JwtUtils {
    private static final String SECRET_KEY = "www.examSystem.smileShark.com";

    // 解析JWT并反序列化为目标类型
    public static <T> T parseJwt(String jwt, Class<T> clazz) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwt)
                    .getBody();

            T instance = clazz.getDeclaredConstructor().newInstance(); // 创建目标类的实例
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true); // 确保可以访问私有字段
                if (claims.containsKey(field.getName())) { // 检查claims中是否存在字段名
                    Object value = claims.get(field.getName()); // 获取字段值

                    // 检查值是否为null
                    if (value == null) {
                        System.out.println("Field " + field.getName() + " is null, skipping...");
                        continue; // 跳过null值
                    }

                    // 根据字段类型进行反序列化
                    if (field.getType() == Integer.class) {
                        value = Integer.parseInt(value.toString());
                    } else if (field.getType() == String.class) {
                        value = value.toString();
                    }

                    field.set(instance, value); // 将值赋给字段
                }
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 创建JWT
    public static String createJwt(Object object) {
        HashMap<String, Object> claims = new HashMap<>();
        Class<?> aClass = object.getClass();
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true); // 确保可以访问私有字段
            try {
                Object value = field.get(object); // 获取字段值
                claims.put(field.getName(), value); // 将字段名和值存储到claims中
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 生成JWT
        return Jwts.builder()
                .setClaims(claims)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .compact();
    }
}