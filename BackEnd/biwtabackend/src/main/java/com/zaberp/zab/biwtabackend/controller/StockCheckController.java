package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.dto.StockCheckDto;
import com.zaberp.zab.biwtabackend.service.StockCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockCheckController {
    @Autowired
    private StockCheckService service;

    @GetMapping
    public ResponseEntity<List<StockCheckDto>> getStocks(
            @RequestParam Long zid,
            @RequestParam String xwh,
            @RequestParam String xitem,
            @RequestParam String xgitem
    ) {

        return ResponseEntity.ok(service.getFilteredStock(zid, xwh,xitem, xgitem));
    }
}

