package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.SubsectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubsectionController {
    private final SubsectionService subsectionService;
    @RequestMapping("/teacher/subsections/list/name/id")
    public String selectAllSubsectionsNameAndIdByChapterId(@RequestBody RequestParams requestParams){
        return subsectionService.selectAllSubsectionsNameAndIdByChapterId(requestParams);
    }
    @RequestMapping("/teacher/subsection/info")
    public String selectAllSubsectionInfoBySubsectionId(@RequestBody RequestParams requestParams){
        return subsectionService.selectAllSubsectionInfoBySubsectionId(requestParams);
    }
}
