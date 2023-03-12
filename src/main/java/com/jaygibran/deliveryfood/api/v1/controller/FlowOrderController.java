package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.core.security.CheckSecurity;
import com.jaygibran.deliveryfood.domain.service.FlowOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlowOrderController {

    private final FlowOrderService flowOrderService;

    @CheckSecurity.Orders.AllowManageOrders
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/confirmation")
    public void confirm(@PathVariable String orderCode) {
        flowOrderService.confirm(orderCode);
    }

    @CheckSecurity.Orders.AllowManageOrders
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/delivered")
    public void deliver(@PathVariable String orderCode) {
        flowOrderService.deliver(orderCode);
    }

    @CheckSecurity.Orders.AllowManageOrders
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/canceled")
    public void canceled(@PathVariable String orderCode) {
        flowOrderService.cancel(orderCode);
    }
}
