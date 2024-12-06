package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.ApiError;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.service.CommonService;
import com.zaberp.zab.biwtabackend.service.PdmstService;
import com.zaberp.zab.biwtabackend.service.PrimaryKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class PdmstController extends BaseController<Pdmst,PdmstId>{

    private final PdmstService service;
    private final PrimaryKeyService primaryKeyService;
    public PdmstController(PdmstService service, PrimaryKeyService primaryKeyService) {
        this.service = service;
        this.primaryKeyService = primaryKeyService;
    }

    @PostMapping
    public ResponseEntity<?> createPdmst(@RequestBody Pdmst pdmst) {
        try {
            Pdmst savePdmst = service.createPdmst(pdmst);
            return ResponseEntity.ok(savePdmst);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

    @GetMapping("approver/{zid}")
    public List<Pdmst> findbyZidAndZactive(
            @PathVariable int zid
    ) {
        return service.findByZidAndZactive(zid,"1");
    }

    @Override
    protected CommonService<Pdmst, PdmstId> getService() {
        return service;
    }
}

