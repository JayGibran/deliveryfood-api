package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.filter.DailySaleFilter;

public interface SaleReportService {

    byte[] issueDailySales(DailySaleFilter filter, String timeOffSet);
}
