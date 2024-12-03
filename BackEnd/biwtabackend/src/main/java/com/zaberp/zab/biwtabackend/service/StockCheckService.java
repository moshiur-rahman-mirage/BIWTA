package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.dto.StockCheckDto;
import com.zaberp.zab.biwtabackend.repository.StockCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockCheckService {
    @Autowired
    private StockCheckRepository repository;

    public List<StockCheckDto> getFilteredStock(Long zid, String xwh,String xitem, String xgitem) {
        return repository.findFilteredStock(zid, xwh,xitem, xgitem);
    }
}

