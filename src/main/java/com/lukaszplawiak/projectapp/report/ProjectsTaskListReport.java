package com.lukaszplawiak.projectapp.report;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProjectsTaskListReport {
    private Project project;
    private List<Task> tasks;

    public void setProjects(Project project) {
        this.project = project;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void generateProjectsTaskList(HttpServletResponse response) throws IOException {
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDocument = new PdfDocument(writer);
        HeaderEventHandler headerEventHandler = new HeaderEventHandler("Project Management App by Lukasz Plawiak");
        FooterEventHandler footerEventHandler = new FooterEventHandler();
        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, headerEventHandler);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, footerEventHandler);
        pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
        Document document = new Document(pdfDocument);

        Paragraph paragraph = new Paragraph("Project details with tasks");
        paragraph.setFontSize(12);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setBold();
        paragraph.setMargin(10);
        document.add(paragraph);

        float[] projectColumnWidth = {1, 4, 5, 2, 3, 3, 1, 1};
        Table projectTable = new Table(UnitValue.createPercentArray(projectColumnWidth));
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
        projectTable.addCell(project.getId().toString());
        projectTable.addCell(project.getTitle());
        projectTable.addCell(project.getDescription());
        projectTable.addCell(project.getDeadline().toString());
        projectTable.addCell(project.getAudit().getCreatedOn().toString());
        if (project.getAudit().getUpdatedOn() == null) {
            projectTable.addCell(none);
        } else {
            projectTable.addCell(project.getAudit().getUpdatedOn().withSecond(0).toString());
        }
        projectTable.addCell(String.valueOf(project.isDone()));
        projectTable.addCell(project.getUser().getFirstName() + " " + project.getUser().getLastName());
        document.add(projectTable);

        Paragraph paragraph2 = new Paragraph("Tasks");
        paragraph2.setFontSize(12);
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph2);

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
            taskTable.addCell(task.getId().toString());
            taskTable.addCell(task.getName());
            taskTable.addCell(task.getComment());
            taskTable.addCell(task.getDeadline().toString());
            taskTable.addCell(task.getAudit().getCreatedOn().withSecond(0).toString());
            if (project.getAudit().getUpdatedOn() == null) {
                taskTable.addCell(none);
            } else {
                taskTable.addCell(task.getAudit().getUpdatedOn().withSecond(0).toString());
            }
            taskTable.addCell(String.valueOf(task.isDone()));
            taskTable.addCell(task.getUser().getFirstName() + " " + task.getUser().getLastName());
        }
        document.add(taskTable);
        document.close();
    }
}
