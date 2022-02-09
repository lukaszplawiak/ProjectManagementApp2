package com.lukaszplawiak.projectapp.report;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class FooterEventHandler implements IEventHandler {
    protected PdfFormXObject placeholder;
    protected final float side = 20;

    public FooterEventHandler() {
        placeholder = new PdfFormXObject(new Rectangle(0, 0, side, side));
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        int pageNumber = pdf.getPageNumber(page);
        int numberOfPages = pdf.getNumberOfPages();
        Rectangle pageSize = page.getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Canvas canvas = new Canvas(pdfCanvas, pageSize);
        canvas.setFontSize(8);
        Paragraph p = new Paragraph()
                .add("Page ")
                .add(String.valueOf(pageNumber))
                .add(" of ")
                .add(String.valueOf(numberOfPages));
        canvas.showTextAligned(p,
                pageSize.getWidth() / 2, pageSize.getBottom() + side,
                TextAlignment.CENTER);
        canvas.close();
        pdfCanvas.release();
    }
}