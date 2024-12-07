package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.PdDependentId;
import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.id.XcodesId;
import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.service.CommonService;
import com.zaberp.zab.biwtabackend.service.PdDependentService;
import com.zaberp.zab.biwtabackend.service.PdmstService;
import com.zaberp.zab.biwtabackend.service.XcodesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/dependent")
public class PdDependentController extends BaseController<PdDependent,PdDependentId>{

    private final PdDependentService service;

    public PdDependentController(PdDependentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createPdDependent(@RequestBody PdDependent pdDependent) {
        // Ensure that the composite key is set
//        if (pdDependent.getZid() == 0) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Validation failed: Composite key (zid, xstaff) is missing.");
//        }
//        if (pdDependent.getZid() == 0) {
//            return ResponseEntity.status(HttpStatus.MULTI_STATUS)
//                    .body("Validation failed: 'zid' is mandatory.");
//        }
//        if (pdDependent.getXstaff() == null || pdDependent.getXstaff().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Validation failed: Employee ID (xstaff) is missing.");
//        }
        Integer maxXrow = service.getMaxXrowByZidAndXstaff(pdDependent.getZid(), pdDependent.getXstaff());
        System.out.println(maxXrow);
        pdDependent.setXrow(maxXrow + 1);
        PdDependent savedPdDependent = service.save(pdDependent);

        return ResponseEntity.ok(savedPdDependent);
    }

    @Override
    protected CommonService<PdDependent, PdDependentId> getService() {
        return service;
    }
}

