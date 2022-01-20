package com.lukaszplawiak.projectapp.report;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.lukaszplawiak.projectapp.dto.UserResponseDto;
import com.lukaszplawiak.projectapp.model.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserListReport {
    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void generateUserList(HttpServletResponse response) throws IOException {
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDocument = new PdfDocument(writer);
        HeaderEventHandler headerEventHandler = new HeaderEventHandler("Project Management App by Lukasz Plawiak");
        FooterEventHandler footerEventHandler = new FooterEventHandler();
        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, headerEventHandler);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, footerEventHandler);

        Document document = new Document(pdfDocument);
        Paragraph paragraph = new Paragraph("Employee's list");
        paragraph.setFontSize(12);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setBold();
        paragraph.setMargin(10);
        document.add(paragraph);
        // Add table
        Table table = new Table(4);
        table.setWidth(UnitValue.createPercentValue(100));
        table.setFontSize(10);
        table.addHeaderCell("ID");
        table.addHeaderCell("First Name");
        table.addHeaderCell("Last Name");
        table.addHeaderCell("Email");

        for (User user : users) {
            table.addCell(user.getId().toString());
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getEmail());
        }
        document.add(table);
        document.close();
    }
}
