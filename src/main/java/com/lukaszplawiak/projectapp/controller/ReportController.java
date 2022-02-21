package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.report.ReportGenerationService;
import com.lukaszplawiak.projectapp.report.ReportProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping(path = "/api/v1/reports")
class ReportController {
    private final ReportGenerationService reportGenerationService;

    ReportController(ReportGenerationService reportGenerationService) {
        this.reportGenerationService = reportGenerationService;
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping
    ResponseEntity<Resource> createReport(@RequestBody ReportProperties reportProperties) {
        File report = reportGenerationService.generateReport(reportProperties);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(CONTENT_DISPOSITION, prepareContentDispositionHeader(report))
                .body(new FileSystemResource(report));
    }

    private String prepareContentDispositionHeader(File report) {
        return "attachment; filename=" + report.getName();
    }
}