package com.lukaszplawiak.projectapp.report;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.lukaszplawiak.projectapp.model.Project;

import java.time.LocalDateTime;
import java.util.List;

public class DoneProjectsReportGenerator extends ReportGenerator {
    private final List<Project> projects;

    public DoneProjectsReportGenerator(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    protected String getReportName() {
        return "Done_Projects_List_Report_" + LocalDateTime.now().withSecond(0).withNano(0);
    }

    @Override
    protected PageSize getPageSize() {
        return PageSize.A4.rotate();
    }

    @Override
    protected void writeTitle(Document document) {
        Paragraph paragraph = new Paragraph("Projects Done List");
        paragraph.setFontSize(12);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setBold();
        paragraph.setMargin(10);
        document.add(paragraph);
    }

    @Override
    protected void writeContent(Document document) {
        float[] columnWidth = {1, 12, 3, 4, 4, 2, 1};
        Table table = new Table(UnitValue.createPercentArray(columnWidth));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setFontSize(8);
        table.addHeaderCell("ID");
        table.addHeaderCell("Title");
        table.addHeaderCell("Deadline");
        table.addHeaderCell("Created");
        table.addHeaderCell("Updated");
        table.addHeaderCell("Done");
        table.addHeaderCell("Employee");

        String none = "not updated";
        for (Project project : projects) {
            table.addCell(project.getId().toString());
            table.addCell(project.getTitle());
            table.addCell(project.getDeadline().toString());
            table.addCell(project.getAudit().getCreatedOn().withSecond(0).toString());
            if (project.getAudit().getUpdatedOn() == null) {
                table.addCell(none);
            } else {
                table.addCell(project.getAudit().getUpdatedOn().withSecond(0).toString());
            }
            table.addCell(String.valueOf(project.isDone()));
            table.addCell(project.getUser().getFirstName() + " " + project.getUser().getLastName());
        }
        document.add(table);
        document.close();
    }
}