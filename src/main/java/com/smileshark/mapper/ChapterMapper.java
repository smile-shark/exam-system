package com.smileshark.mapper;

import com.smileshark.entity.question.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChapterMapper {
    List<Chapter> selectAllChaptersNameTitleAndIdByCourseId(@Param("courseId") String courseId);
    List<Chapter> selectAllChaptersIdByCourseId(@Param("courseId") String courseId);
    Chapter selectChapterInfoByChapterId(@Param("chapterId") String chapterId);
    List<Chapter> selectChaptersInCourseIds(@Param("courseIds")List<String> courseIds);
    List<Chapter> selectChaptersByCourseIdAndSubsections(@Param("courseId")String courseId);
    int insertChapter(@Param("chapters")List<Chapter> chapters);
}
