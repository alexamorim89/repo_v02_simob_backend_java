package br.com.simobapi.domain.service;

import br.com.simobapi.domain.enums.TipoService;
import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportService {

    void downloadPDF(String flag, TipoService serviceType, HttpServletResponse response) throws DocumentException, IOException;
}
