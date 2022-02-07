package com.lukaszplawiak.projectapp.report;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.lukaszplawiak.projectapp.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class AllUsersReportGenerator extends ReportGenerator {
    private final List<User> users;

    public AllUsersReportGenerator(List<User> users) {
        this.users = users;
    }

    @Override
    protected String getReportName() {
        return "Employees_List_Report_" + LocalDateTime.now().withSecond(0).withNano(0);
    }

    @Override
    protected PageSize getPageSize() {
        return PageSize.A4;
    }

    @Override
    protected void writeTitle(Document document) {
        Paragraph paragraph = new Paragraph("Employee's list");
        paragraph.setFontSize(12);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setBold();
        paragraph.setMargin(10);
        document.add(paragraph);
    }

    @Override
    protected void writeContent(Document document) {
        Table table = new Table(4);
        table.setWidth(UnitValue.createPercentValue(100));
        table.setFontSize(8);
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
