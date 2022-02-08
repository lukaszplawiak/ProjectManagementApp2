package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.report.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/api/v1/reports/{reportType}")
class ReportController {
    private final ReportGenerationService reportGenerationService;

    ReportController(ReportGenerationService reportGenerationService) {
        this.reportGenerationService = reportGenerationService;
    }

    @GetMapping()
    public ResponseEntity<Resource> getReport(@PathVariable ReportType reportType,
                                              @RequestParam(name = "done", required = false, defaultValue = "true") boolean done,
                                              @RequestParam(name = "id", required = false) String id,
                                              @RequestParam(name = "email", required = false) String email)
    {
        File report = reportGenerationService.generateReport(reportType, done, id , email); //
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(CONTENT_DISPOSITION, prepareContentDispositionHeader(report))
                .body(new FileSystemResource(report));
    }

//    @GetMapping()
//    public ResponseEntity<Resource> getReport(@PathVariable ReportType reportType,
//                                              @RequestParam(defaultValue = "true") boolean done)
//    {
//        File report = reportGenerationService.generateReport(reportType, done); //
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_PDF)
//                .header(CONTENT_DISPOSITION, prepareContentDispositionHeader(report))
//                .body(new FileSystemResource(report));
//    }
//
//    @GetMapping()
//    public ResponseEntity<Resource> getReportById(@PathVariable ReportType reportType,
//                                                  @RequestParam(required = false) String id,
//                                                  @RequestParam(required = false) String email)
//    {
//        File report = reportGenerationService.generateReport(reportType, id, email);
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_PDF)
//                .header(CONTENT_DISPOSITION, prepareContentDispositionHeader(report))
//                .body(new FileSystemResource(report));
//    }

    private String prepareContentDispositionHeader(File report) {
        return "attachment; filename=" + report.getName();
    }

}
