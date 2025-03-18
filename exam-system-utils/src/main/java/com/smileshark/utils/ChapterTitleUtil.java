package com.smileshark.utils;

public class ChapterTitleUtil {
    public static int getChapterTitle(String chapterTitle) {
        // 取出第(*)章中的数字
        int chapterNum = 0;
        try {
            chapterNum = Integer.parseInt(chapterTitle.substring(chapterTitle.indexOf("第") + 1, chapterTitle.indexOf("章")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chapterNum;
    }
}
