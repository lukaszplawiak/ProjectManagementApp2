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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AllProjectListReport {
    private List<Project> projects;

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void generateProjectList(HttpServletResponse response) throws IOException {
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDocument = new PdfDocument(writer);
        HeaderEventHandler headerEventHandler = new HeaderEventHandler("Project Management App by Lukasz Plawiak");
        FooterEventHandler footerEventHandler = new FooterEventHandler();
        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, headerEventHandler);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, footerEventHandler);
        pdfDocument.setDefaultPageSize(PageSize.A4.rotate());

        Document document = new Document(pdfDocument);
        Paragraph paragraph = new Paragraph("All Project's List");
        paragraph.setFontSize(12);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setBold();
        paragraph.setMargin(10);
        document.add(paragraph);

        float[] columnWidth = {1, 12, 3, 4, 4, 2, 1};
        Table table = new Table(UnitValue.createPercentArray(columnWidth));
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("ID");
        table.addHeaderCell("Title");
        table.addHeaderCell("Deadline");
        table.addHeaderCell("Created");
        table.addHeaderCell("Updated");
        table.addHeaderCell("Done");
        table.addHeaderCell("EmpID");

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
            table.addCell(project.getUser().toString());
        }
        document.add(table);
        document.close();
    }
}
