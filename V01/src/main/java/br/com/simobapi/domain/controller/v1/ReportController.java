package br.com.simobapi.domain.controller.v1;

import br.com.simobapi.domain.enums.TipoService;
import br.com.simobapi.domain.service.ReportService;

import com.itextpdf.text.DocumentException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@Tag(name = "REPORT-CONTROLLER Este endpoint emite relatorio em PDF")
@RestController
@RequestMapping(value = "/api/v1/report")
public class ReportController {

    @Autowired
    private ReportService service;


    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Gerar Relatório em PDF de Imovel ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/imovel/export-pdf")
    public void exportToPDF(@RequestHeader("flag_report") String flag, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=imoveis_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        service.downloadPDF(flag.toUpperCase(), TipoService.IMOVEL, response);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Gerar Relatório em PDF de Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/cliente/export-pdf")
    public void exportToPDFA(@RequestHeader("flag_report") String flag, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=clientes_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        service.downloadPDF(flag.toUpperCase(), TipoService.CLIENTE, response);
    }

}
