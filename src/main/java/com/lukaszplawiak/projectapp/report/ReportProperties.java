package com.lukaszplawiak.projectapp.report;

public class ReportProperties {
    private ReportType reportType;
    private boolean done;
    private Long id;
    private String email;

    public ReportProperties(ReportType reportType, boolean done, Long id, String email) {
        this.reportType = reportType;
        this.done = done;
        this.id = id;
        this.email = email;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public boolean isDone() {
        return done;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
