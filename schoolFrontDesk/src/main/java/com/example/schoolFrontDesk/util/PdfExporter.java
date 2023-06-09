package com.example.schoolFrontDesk.util;

import com.example.schoolFrontDesk.dto.Response.StudentResponse;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PdfExporter {

    private List<StudentResponse> students;
    public PdfExporter(List<StudentResponse> students){
        this.students = students;
    }
    private void Header(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date of Birth", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Age", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Joined Date", font));
        table.addCell(cell);
    }

    private void Data(PdfPTable table){
        for (StudentResponse student: students){
            table.addCell(String.valueOf(student.getId()));
            table.addCell(student.getName());
//            table.addCell(student.getDate_of_birth().toString());
//            table.addCell(student.getAge().toString());
//            table.addCell(student.getJoined_date().toString());

        }
    }
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Student", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        Header(table);
        Data(table);

        document.add(table);

        document.close();
    }
}
