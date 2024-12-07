package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.id.CacusId;
import com.zaberp.zab.biwtabackend.model.Cacus;
import com.zaberp.zab.biwtabackend.service.CacusService;
import com.zaberp.zab.biwtabackend.service.CommonService;
import com.zaberp.zab.biwtabackend.service.PrimaryKeyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cacus")
public class CacusController extends BaseController<Cacus, CacusId> {

    @Autowired
    private CacusService cacusService;

    @Autowired
    private PrimaryKeyService primaryKeyService;

    @Override
    protected CommonService<Cacus, CacusId> getService() {
        return cacusService;
    }

    @PostMapping
    public Cacus createCacus(@RequestBody Cacus cacus) {
        String generatedKey = primaryKeyService.getGeneratedPrimaryKey(cacus.getZid(), "Party Code", "PAR-", 6);
        cacus.setXcus(generatedKey);
        cacus.setZtime(LocalDateTime.now());
        cacus.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
        return cacusService.save(cacus);
    }

}
