package br.com.simobapi.domain.util;

import com.itextpdf.text.Font;
import com.itextpdf.text.Element;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFExporter {

    private String nomeRelatorio;


    public PDFExporter(String nomeRelatorio){
        this.nomeRelatorio = nomeRelatorio;
    }


    public void export(PdfPTable table, HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

//        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        writeTitle(document, nomeRelatorio);
        document.add(table);
        document.close();
    }

    public void createTableHeader(PdfPTable table, List<String> nomesColunas){
        for (String header: nomesColunas) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.CYAN);
            cell.setPadding(5);

            Font font = FontFactory.getFont(FontFactory.HELVETICA);
            font.setColor(BaseColor.BLACK);
            table.addCell(cell);
        }
    }

    private void writeTitle(Document document, String nomeRelatorio) throws DocumentException {
        Paragraph p1 = new Paragraph();
        deixarLinhaVazia(p1, 1);

        p1.add(new Paragraph(nomeRelatorio, new Font(Font.FontFamily.COURIER, 20, Font.BOLD)));
        deixarLinhaVazia(p1, 2);

        p1.add(new Paragraph("gerado em "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM HH:mm:ss")),
                new Font(Font.FontFamily.COURIER, 16, Font.BOLD))
        );
        deixarLinhaVazia(p1, 4);

        document.add(p1);
    }

    private static void deixarLinhaVazia(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


}
