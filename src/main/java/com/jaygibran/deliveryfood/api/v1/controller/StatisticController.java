package com.jaygibran.deliveryfood.api.v1.controller;

import java.util.List;

import com.jaygibran.deliveryfood.core.security.CheckSecurity;
import com.jaygibran.deliveryfood.domain.filter.DailySaleFilter;
import com.jaygibran.deliveryfood.domain.model.dto.DailySale;
import com.jaygibran.deliveryfood.domain.service.SaleQueryService;
import com.jaygibran.deliveryfood.domain.service.SaleReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticController {

    private final SaleQueryService saleQueryService;
    private final SaleReportService saleReportService;

    @CheckSecurity.Statistic.AllowQuery
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> queryDailySale(DailySaleFilter filter,
                                          @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return saleQueryService.queryDailySale(filter, timeOffSet);
    }

    @CheckSecurity.Statistic.AllowQuery
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> queryDailySalePdf(DailySaleFilter filter,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        byte[] bytePdf = saleReportService.issueDailySales(filter, timeOffSet);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytePdf);
    }
}
