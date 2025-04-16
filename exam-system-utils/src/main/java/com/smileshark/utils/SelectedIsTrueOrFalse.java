package com.smileshark.utils;

import java.util.List;
import java.util.Objects;

public class SelectedIsTrueOrFalse {
    public static boolean isTrueOrFalse(Object firstObj, Object secondObj) {
        System.out.println(firstObj+"\t对比\t"+secondObj+"\t结果\t"+firstObj.equals(secondObj));
        return firstObj.equals(secondObj);
    }
}
