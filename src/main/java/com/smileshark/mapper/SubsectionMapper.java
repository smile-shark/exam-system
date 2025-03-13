package com.smileshark.mapper;

import com.smileshark.entity.question.Subsection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubsectionMapper {
    List<Subsection> selectAllSubsectionsNameAndIdByChapterId(@Param("chapterId")String chapterId);
    List<Subsection> selectAllSubsectionsIdByChapterId(@Param("chapterId")String chapterId);
    Subsection selectAllSubsectionInfoBySubsectionId(@Param("subsectionId")String subsectionId);
    List<Subsection> selectSubsectionsInChapterIds(@Param("chapterIds")List<String> chapterIds);
}
