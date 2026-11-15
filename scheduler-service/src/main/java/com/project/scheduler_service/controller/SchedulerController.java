package com.project.scheduler_service.controller;

import com.project.scheduler_service.dto.response.BaseResponse;
import com.project.scheduler_service.service.ImportSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchedulerController {

    private final ImportSchedulerService importSchedulerService;

    @PostMapping("/import")
    public BaseResponse<List<Object>> triggerManualImport() {
        log.info("[SchedulerController] Impor manual dipicu melalui endpoint /scheduler/import");
        return importSchedulerService.doImport();
    }
}