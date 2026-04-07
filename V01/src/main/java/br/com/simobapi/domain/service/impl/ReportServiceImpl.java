package br.com.simobapi.domain.service.impl;

import br.com.simobapi.domain.controller.v1.vo.report.ClienteReportVO;
import br.com.simobapi.domain.controller.v1.vo.report.ImovelReportVO;
import br.com.simobapi.domain.adapter.ReportAdapter;
import br.com.simobapi.domain.entity.ClientePessoaFisicaEntity;
import br.com.simobapi.domain.entity.ClientePessoaJuridicaEntity;
import br.com.simobapi.domain.enums.TipoReportCliente;
import br.com.simobapi.domain.enums.TipoReportImovel;
import br.com.simobapi.domain.enums.TipoService;
import br.com.simobapi.domain.enums.TipoVenda;
import br.com.simobapi.domain.repository.ClienteRepository;
import br.com.simobapi.domain.repository.ImovelRepository;
import br.com.simobapi.domain.service.ReportService;
import br.com.simobapi.domain.util.PDFExporter;
import br.com.simobapi.domain.util.ReportUtil;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    ReportAdapter adapter;


    @Override
    public void downloadPDF(String flag, TipoService serviceType, HttpServletResponse response) throws DocumentException, IOException {
        switch (serviceType){
            case IMOVEL -> {
                reportImovel(TipoReportImovel.valueOf(flag), response);
                break;
            }
            case CLIENTE -> {
               reportCliente(TipoReportCliente.valueOf(flag), response);
                break;
            }
        }
    }

    private void reportImovel(TipoReportImovel flag, HttpServletResponse response) throws DocumentException, IOException {
        var imoveis = filtraImoveis(flag);

        PDFExporter pdfExporter = new PDFExporter(ReportUtil.NOME_RELATORIO_IMOVEL.concat(flag.getDescricao()));

        PdfPTable table = new PdfPTable(ReportUtil.NUMERO_DE_COLUNAS_IMOVEL);
        pdfExporter.createTableHeader(table, ReportUtil.LISTA_NOMES_COLUNAS_IMOVEL);

        for (ImovelReportVO imovel: imoveis) {
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(imovel.getNome());
            table.addCell(imovel.getTipoImovel());
            table.addCell(imovel.getStatus());
            table.addCell(imovel.getValorMensal());
            table.addCell(imovel.getValor());
        }

        pdfExporter.export(table, response);
    }

    private void reportCliente(TipoReportCliente flag, HttpServletResponse response) throws DocumentException, IOException {
        var clientes = filtraClientes(flag);

        PDFExporter pdfExporter = new PDFExporter(ReportUtil.NOME_RELATORIO_CLIENTE.concat(flag.getDescricao()));

        PdfPTable table = new PdfPTable(ReportUtil.NUMERO_DE_COLUNAS_CLIENTE);
        pdfExporter.createTableHeader(table, ReportUtil.LISTA_NOMES_COLUNAS_CLIENTE);

        for (ClienteReportVO cliente: clientes) {
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(cliente.getNome());
            table.addCell(cliente.getTelefonePrincipal());
            table.addCell(cliente.getTelefoneSecundario());
            table.addCell(cliente.getEmail());
            table.addCell(cliente.getEndereço());
        }

        pdfExporter.export(table, response);
    }

    private List<ClienteReportVO> filtraClientes(TipoReportCliente flag) {
        List<ClienteReportVO> filtro = null;
        var clientes = clienteRepository.findAll();

        switch (flag){
            case PESSOA_FISICA, FISICA -> {
                filtro =  adapter.toListClienteVO( clientes.stream().filter(c -> c instanceof ClientePessoaFisicaEntity).toList() );
                break;
            }
            case PESSOA_JURIDICA, JURIDICA -> {
                filtro = adapter.toListClienteVO( clientes.stream().filter(c -> c instanceof ClientePessoaJuridicaEntity).toList() );
                break;
            }
            case TODOS -> {
                filtro =  adapter.toListClienteVO( clientes.stream().toList() );
            }
        }

        return filtro;
    }

    private List<ImovelReportVO> filtraImoveis(TipoReportImovel flag){
        List<ImovelReportVO> filtro = null;
        var imoveis = imovelRepository.findAll();

        switch (flag){
            case ALUGUEL -> {
                filtro =  adapter.toListImovelVO( imoveis.stream().filter(i -> i.getTipoVenda().equals(TipoVenda.ALUGUEL)).toList() );
                break;
            }
            case VENDA -> {
                filtro = adapter.toListImovelVO( imoveis.stream().filter(i -> i.getTipoVenda().equals(TipoVenda.VENDA)).toList() );
                break;
            }
            case TODOS -> {
                filtro =  adapter.toListImovelVO( imoveis.stream().toList() );
            }
        }

        return filtro;
    }

}
