package com.isfood.infrastructure.repository.service.report;

import com.isfood.domain.filter.DailySaleFilter;
import com.isfood.domain.service.SaleQueryService;
import com.isfood.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfSaleReportService implements SaleReportService {
    @Autowired
    private SaleQueryService saleQueryService;

    @Override
    public byte[] emmitDailySales(DailySaleFilter filter, String timeOffset){
        try {
            var inputStream = this.getClass().getResourceAsStream(
                                                "/relatorios/vendas-diarias.jasper");

            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt","BR"));

            var dailySales = saleQueryService.consultDailySales(filter, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(dailySales);
            JasperPrint jasperPrint = null;

            jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possivel gerar relatório", e);
        }
    }
}
