package br.com.simobapi.domain.util;

import br.com.simobapi.domain.entity.ImovelEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PDFGenerator {

    private static final Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
    private static final Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
    private static final Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);

    private static final String NOME_DO_RELATORIO = "RELATORIO DE IMOVEL";
    private static final int NUMERO_DE_COLUNAS = 4;
    private static final List<String> NOMES_DAS_COLUNAS = List.of("NOME", "STATUS", "TIPO DE IMOVEL", "VALOR MENSAL", "VALOR");


    public Document generatePdfReport(List<ImovelEntity> imoveis) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(getPdfNameWithDate()));
            document.open();
//          adicionaLogo(document);
            adicionarTitulo(document);
            criarTabela(imoveis, document, NUMERO_DE_COLUNAS);
            adicionarRodape(document);
            document.close();
            System.out.println("------------------Your PDF Report is ready!-------------------------");

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }

        return document;
    }


    private void adicionarTitulo(Document document) throws DocumentException {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM HH:mm:ss"));
        Paragraph p1 = new Paragraph();
        deixarLinhaVazia(p1, 1);
        p1.add(new Paragraph("nome_do_relatorio", COURIER));
        p1.setAlignment(Element.ALIGN_CENTER);
        deixarLinhaVazia(p1, 1);
        p1.add(new Paragraph("Relatório Gerado em " + localDateString, COURIER_SMALL));

        document.add(p1);
    }

    private void criarTabela(List<ImovelEntity> imoveis, Document document, int noOfColumns) throws DocumentException {
        Paragraph paragrafo = new Paragraph();
        deixarLinhaVazia(paragrafo, 3);
        document.add(paragrafo);

        PdfPTable table = new PdfPTable(noOfColumns);

        for(int i=0; i<noOfColumns; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(NOMES_DAS_COLUNAS.get(i)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
        }

        table.setHeaderRows(1);
        populaTabela(table, imoveis);
        document.add(table);
    }

    private void populaTabela(PdfPTable table, List<ImovelEntity> imoveis) {

//        List<Employee> list = eRepo.getAllEmployeeData();
        for (ImovelEntity imovel : imoveis) {

            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(imovel.getNome());
            table.addCell(imovel.getStatus().toString());
            table.addCell(imovel.getTipoImovel().toString());
            table.addCell(imovel.getValorMensal().toString());
            table.addCell(imovel.getValor().toString());

//            System.out.println(employee.getEmpName());
        }

    }

    private void adicionarRodape(Document document) throws DocumentException {
        Paragraph p2 = new Paragraph();
        deixarLinhaVazia(p2, 3);
        p2.setAlignment(Element.ALIGN_MIDDLE);
        p2.add(new Paragraph(
                "------------------------End Of " + NOME_DO_RELATORIO +"------------------------",
                COURIER_SMALL_FOOTER));

        document.add(p2);
    }

    private static void deixarLinhaVazia(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private String getPdfNameWithDate() {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM HH:mm:ss"));
        return NOME_DO_RELATORIO+".pdf";
//        return NOME_DO_RELATORIO +"-"+localDateString+".pdf";
    }

}
