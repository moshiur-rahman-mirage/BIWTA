package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.PdDependentId;
import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.id.XcodesId;
import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.service.PdDependentService;
import com.zaberp.zab.biwtabackend.service.PdmstService;
import com.zaberp.zab.biwtabackend.service.XcodesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pddependent")
public class PdDependentController {

    private final PdDependentService service;

    public PdDependentController(PdDependentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createPdDependent(@RequestBody PdDependent pdDependent) {
        if (service.existsByZidAndXstaffAndXrow(pdDependent.getZid(), pdDependent.getXstaff(),pdDependent.getXrow())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: A Employee with the same staff id and relation already exists.");
        }



        PdDependent savePdDependent = service.save(pdDependent);
        return ResponseEntity.ok(savePdDependent);
    }

    @PutMapping
    public ResponseEntity<?> updatePdDependent(
            @RequestParam Integer zid,
            @RequestParam String xstaff,
            @RequestParam int xrow,
            @RequestBody PdDependent updatedPdDependent) {

        if (updatedPdDependent.getXrelation() == null || updatedPdDependent.getXrelation().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Validation failed: Relation field is required.");
        }

        PdDependentId id = new PdDependentId(zid, xstaff,xrow);
        return service.findById(id)
                .map(existingPdmst -> {
                    updatedPdDependent.setZid(zid);
                    updatedPdDependent.setXstaff(xstaff);
                    updatedPdDependent.setXrow(xrow);

                    return ResponseEntity.ok(service.save(updatedPdDependent));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping
    public ResponseEntity<Void> deletePdDependent(
            @RequestParam int zid,
            @RequestParam String xstaff,@RequestParam int xrow) {
        PdDependentId id = new PdDependentId(zid, xstaff,xrow);

        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/search")
    public List<PdDependent> searchPdDependent(

            @RequestParam Integer zid,
            @RequestParam(required = false) String xstaff,
            @RequestParam(required = false) String xrelation

    ) {
        return service.findPdDependent(zid,xstaff,xrelation);
    }

    @GetMapping("/searchtext")
    public List<PdDependent> search(
            @RequestParam("zid") String zid,
            @RequestParam("searchText") String searchText
    ) {
        return service.searchByText(zid, searchText);

    }


}

