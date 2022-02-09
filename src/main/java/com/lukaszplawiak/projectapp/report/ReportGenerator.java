package com.lukaszplawiak.projectapp.report;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class ReportGenerator {

    public final File generate() {
        Path reportTempFilePath = allocateTempFile();
        PdfWriter writer = createWriter(reportTempFilePath);

        PdfDocument pdfDocument = new PdfDocument(writer);
        writeHeaderAndFooter(pdfDocument);

        Document document = new Document(pdfDocument);
        writeTitle(document);
        writeContent(document);

        document.close();

        return reportTempFilePath.toFile();
    }

    private PdfWriter createWriter(Path reportTempFilePath) {
        try {
            return new PdfWriter(reportTempFilePath.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path allocateTempFile() {
        try {
            File file = new File("/tmp/" + getReportName() + ".pdf");
            file.createNewFile();
            return file.toPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getReportName();

    protected abstract PageSize getPageSize();

    protected abstract void writeTitle(Document document);

    protected abstract void writeContent(Document document);

    private void setPageSize(PdfDocument pdfDocument) {
        pdfDocument.setDefaultPageSize(getPageSize());
    }

    private void writeHeaderAndFooter(PdfDocument pdfDocument) {
        HeaderEventHandler headerEventHandler = new HeaderEventHandler();
        FooterEventHandler footerEventHandler = new FooterEventHandler();
        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, headerEventHandler);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, footerEventHandler);
    }
}