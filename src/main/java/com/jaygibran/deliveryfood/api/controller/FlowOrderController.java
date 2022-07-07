package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.domain.service.FlowOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/orders/{orderCode}")
public class FlowOrderController {

    private final FlowOrderService flowOrderService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/confirmation")
    public void confirm(@PathVariable String orderCode) {
        flowOrderService.confirm(orderCode);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/delivered")
    public void deliver(@PathVariable String orderCode) {
        flowOrderService.deliver(orderCode);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/canceled")
    public void canceled(@PathVariable String orderCode) {
        flowOrderService.cancel(orderCode);
    }
}
