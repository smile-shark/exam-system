package com.smileshark.utils;

import java.util.List;
import java.util.Objects;

public class SelectedIsTrueOrFalse {
    public static boolean isTrueOrFalse(Object firstObj, Object secondObj) {
        boolean equals = firstObj.equals(secondObj);
//        System.out.println(firstObj+"\t对比\t"+secondObj+"\t结果\t"+equals);
        return equals;
    }
}
