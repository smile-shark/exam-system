package com.smileshark.utils;

import java.util.Arrays;

public class VagueUtil {
    public static String createVagueString(String str) {
        return "%" + String.join("%", str.split("")) + "%";
    }
}
