package com.lukaszplawiak.projectapp.report;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import com.lukaszplawiak.projectapp.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class DetailsUserReportGenerator extends ReportGenerator {
    private final User user;
    private final List<Project> projects;
    private final List<Task> tasks;

    public DetailsUserReportGenerator(User user, List<Project> projects, List<Task> tasks) {
        this.user = user;
        this.projects = projects;
        this.tasks = tasks;
    }

    @Override
    protected String getReportName() {
        return "Details_User_Report_" + LocalDateTime.now().withSecond(0).withNano(0);
    }

    @Override
    protected PageSize getPageSize() {
        return PageSize.A4.rotate();
    }

    @Override
    protected void writeTitle(Document document) {
        Paragraph paragraph = new Paragraph("User details with tasks");
        paragraph.setFontSize(12);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setBold();
        paragraph.setMargin(10);
        document.add(paragraph);
    }

    @Override
    protected void writeContent(Document document) {
        Table userTable = new Table(4);
        userTable.setWidth(UnitValue.createPercentValue(100));
        userTable.setFontSize(8);
        userTable.addHeaderCell("ID");
        userTable.addHeaderCell("First Name");
        userTable.addHeaderCell("Last Name");
        userTable.addHeaderCell("Email");

        userTable.addCell(user.getId().toString());
        userTable.addCell(user.getFirstName());
        userTable.addCell(user.getLastName());
        userTable.addCell(user.getEmail());
        document.add(userTable);


        Paragraph paragraph2 = new Paragraph("Projects");
        paragraph2.setFontSize(12);
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph2);

        float[] columnWidth = {1, 4, 5, 2, 3, 3, 1, 1};
        Table projectTable = new Table(UnitValue.createPercentArray(columnWidth));
        projectTable.setWidth(UnitValue.createPercentValue(100));
        projectTable.setFontSize(8);
        projectTable.addHeaderCell("ID");
        projectTable.addHeaderCell("Title");
        projectTable.addHeaderCell("Description");
        projectTable.addHeaderCell("Deadline");
        projectTable.addHeaderCell("Created");
        projectTable.addHeaderCell("Updated");
        projectTable.addHeaderCell("Done");
        projectTable.addHeaderCell("Employee");

        String none = "not updated";
        for (Project project : projects) {
            if (project.getUser().getId() == user.getId()) {
                projectTable.addCell(project.getId().toString());
                projectTable.addCell(project.getTitle());
                projectTable.addCell(project.getDescription());
                projectTable.addCell(project.getDeadline().toString());
                projectTable.addCell(project.getAudit().getCreatedOn().withSecond(0).toString());
                if (project.getAudit().getUpdatedOn() == null) {
                    projectTable.addCell(none);
                } else {
                    projectTable.addCell(project.getAudit().getUpdatedOn().withSecond(0).toString());
                }
                projectTable.addCell(String.valueOf(project.isDone()));
                projectTable.addCell(project.getUser().getFirstName() + " " + project.getUser().getLastName());
            }
        }
        document.add(projectTable);


        Paragraph paragraph3 = new Paragraph("Tasks");
        paragraph3.setFontSize(12);
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph3);

        float[] taskColumnWidth = {1, 4, 5, 2, 3, 3, 1, 1};
        Table taskTable = new Table(UnitValue.createPercentArray(taskColumnWidth));
        taskTable.setWidth(UnitValue.createPercentValue(100));
        taskTable.setFontSize(8);
        taskTable.addHeaderCell("ID");
        taskTable.addHeaderCell("Name");
        taskTable.addHeaderCell("Comment");
        taskTable.addHeaderCell("Deadline");
        taskTable.addHeaderCell("Created");
        taskTable.addHeaderCell("Updated");
        taskTable.addHeaderCell("Done");
        taskTable.addHeaderCell("Employee");

        for (Task task : tasks) {
            if (task.getUser().getId() == user.getId()) {
                taskTable.addCell(task.getId().toString());
                taskTable.addCell(task.getName());
                taskTable.addCell(task.getComment());
                taskTable.addCell(task.getDeadline().toString());
                taskTable.addCell(task.getAudit().getCreatedOn().withSecond(0).toString());
                if (task.getAudit().getUpdatedOn() == null) {
                    taskTable.addCell(none);
                } else {
                    taskTable.addCell(task.getAudit().getUpdatedOn().withSecond(0).toString());
                }
                taskTable.addCell(String.valueOf(task.isDone()));
                taskTable.addCell(task.getUser().getFirstName() + " " + task.getUser().getLastName());
            }
        }
        document.add(taskTable);
        document.close();
    }
}