package com.isfood.domain.service;

import com.isfood.domain.filter.DailySaleFilter;
import net.sf.jasperreports.engine.JRException;

public interface SaleReportService {

    byte[] emmitDailySales(DailySaleFilter filter, String timeOffset);
}
