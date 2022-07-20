package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.domain.filter.DailySaleFilter;
import com.jaygibran.deliveryfood.domain.model.dto.DailySale;
import com.jaygibran.deliveryfood.domain.service.SaleQueryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/statistics")
public class StatisticController {

    private final SaleQueryService saleQueryService;

    @GetMapping("/daily-sales")
    public List<DailySale> queryDailySale(DailySaleFilter filter) {
        return saleQueryService.queryDailySale(filter);
    }
}
