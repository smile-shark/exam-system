package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChapterController {
    private final ChapterService chapterService;
    @RequestMapping("/teacher/chapter/list/name/title/id")
    public String selectAllChaptersNameTitleAndIdByCourseId(@RequestBody RequestParams requestParams) {
        return chapterService.selectAllChaptersNameTitleAndIdByCourseId(requestParams);
    }
    @RequestMapping("/teacher/chapter/list/name/title/id/in/courseIds")
    public String selectAllChaptersNameTitleAndIdInCourseId(@RequestBody RequestParams requestParams) {
        return chapterService.selectAllChaptersNameTitleAndIdInCourseId(requestParams);
    }
    @RequestMapping("/teacher/chapter/insert/insertChapters")
    public String insertChapters(@RequestBody RequestParams requestParams) {
        return chapterService.insertChapters(requestParams);
    }
    @RequestMapping("/teacher/chapter/update/updateChapter")
    public String updateChapter(@RequestBody RequestParams requestParams) {
        return chapterService.updateChapter(requestParams);
    }
    @RequestMapping("/teacher/chapter/delete/deleteChapter")
    public String deleteChapter(@RequestBody RequestParams requestParams) {
        return chapterService.deleteChapter(requestParams);
    }
}
