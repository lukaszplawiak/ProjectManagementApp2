package com.lukaszplawiak.projectapp.report;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.properties.TextAlignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HeaderEventHandler implements IEventHandler {
    private static final String HEADER = "Project Management App by Lukasz Plawiak";

    @Override
    public void handleEvent(Event event) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formatDateTime = dateFormat.format(currentDateTime);
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();
        Canvas canvas = new Canvas(new PdfCanvas(page), pageSize);
        canvas.setFontSize(8);
        canvas.showTextAligned(HEADER + "   " + formatDateTime,
                pageSize.getWidth() / 2, pageSize.getTop() - 26,
                TextAlignment.RIGHT);
        canvas.close();
    }
}