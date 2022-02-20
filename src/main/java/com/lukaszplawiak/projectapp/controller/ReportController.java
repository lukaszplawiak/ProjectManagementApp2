package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.report.ReportGenerationService;
import com.lukaszplawiak.projectapp.report.ReportType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.File;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping(path = "/api/v1/reports/{reportType}")
class ReportController {
    private final ReportGenerationService reportGenerationService;

    ReportController(ReportGenerationService reportGenerationService) {
        this.reportGenerationService = reportGenerationService;
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping
    public ResponseEntity<Resource> getReport(@PathVariable ReportType reportType,
                                              @RequestParam(defaultValue = "true") boolean done)
    {
        File report = reportGenerationService.generateReport(reportType, done); /////////
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(CONTENT_DISPOSITION, prepareContentDispositionHeader(report))
                .body(new FileSystemResource(report));
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping(path = "/{id}")
    public ResponseEntity<Resource> getReport(@PathVariable ReportType reportType,
                                                  @PathVariable(required = false) String id)
    {
        File report = reportGenerationService.generateReport(reportType, id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(CONTENT_DISPOSITION, prepareContentDispositionHeader(report))
                .body(new FileSystemResource(report));
    }

    private String prepareContentDispositionHeader(File report) {
        return "attachment; filename=" + report.getName();
    }
}