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
}
