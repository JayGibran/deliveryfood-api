package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.filter.DailySaleFilter;
import com.jaygibran.deliveryfood.domain.model.dto.DailySale;

import java.util.List;

public interface SaleQueryService {

    List<DailySale> queryDailySale(DailySaleFilter filter);
}
