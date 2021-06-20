package com.isfood.api.controller;

import com.isfood.domain.entity.dto.DailySale;
import com.isfood.domain.filter.DailySaleFilter;
import com.isfood.domain.service.SaleQueryService;
import com.isfood.domain.service.SaleReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatistics")
public class EstatisticsController {

    @Autowired
    private SaleQueryService saleQueryService;

    @Autowired
    private SaleReportService saleReportService;

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> consultDailySales(DailySaleFilter filter,
                                             @RequestParam(required = false, defaultValue = "+00:00")String timeOffset){
        return saleQueryService.consultDailySales(filter, timeOffset);

    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultDailySalesPDF(DailySaleFilter filter,
                                                       @RequestParam(required = false, defaultValue = "+00:00")String timeOffset){
        byte[] bytesPdf = saleReportService.emmitDailySales(filter, timeOffset);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);

    }
}
