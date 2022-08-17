package com.jaygibran.deliveryfood.infrastructure.service.report;

import com.jaygibran.deliveryfood.domain.filter.DailySaleFilter;
import com.jaygibran.deliveryfood.domain.service.SaleQueryService;
import com.jaygibran.deliveryfood.domain.service.SaleReportService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@AllArgsConstructor
@Service
public class PdfSalesReportService implements SaleReportService {

    private final SaleQueryService saleQueryService;

    @Override
    public byte[] issueDailySales(DailySaleFilter filter, String timeOffSet) {

        try {
            var inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");

            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("en", "ie"));

            var dailySale = saleQueryService.queryDailySale(filter, timeOffSet);

            var dataSource = new JRBeanCollectionDataSource(dailySale);

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportException("It was not possible issue the Daily Sales report", e);
        }
    }
}
