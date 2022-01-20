package com.lukaszplawiak.projectapp.report;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.properties.TextAlignment;

class HeaderEventHandler implements IEventHandler {
    private String header;

    public HeaderEventHandler(String header) {
        this.header = header;
    }
    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();
        Canvas canvas = new Canvas(new PdfCanvas(page), pageSize);
        canvas.setFontSize(8);
        canvas.showTextAligned(header,
                pageSize.getLeft() + 200, pageSize.getTop() - 26,
                TextAlignment.RIGHT);
        canvas.close();
    }
}