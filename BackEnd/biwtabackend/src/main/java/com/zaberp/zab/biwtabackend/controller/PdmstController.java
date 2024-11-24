package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.id.XcodesId;
import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.service.PdmstService;
import com.zaberp.zab.biwtabackend.service.XcodesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pdmst")
public class PdmstController {

    private final PdmstService service;

    public PdmstController(PdmstService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createPdmst(@RequestBody Pdmst pdmst) {
        if (service.existsByZidAndXstaffAndXposition(pdmst.getZid(), pdmst.getXstaff(),pdmst.getXposition())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: A Employee with the same staff id and position already exists.");
        }

        if (service.existsByZidAndXmobile(pdmst.getZid(), pdmst.getXmobile())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: A Employee with the same mobile number already exists.");
        }
        if (service.existsByZidAndXposition(pdmst.getZid(), pdmst.getXposition())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: A Employee with the same position id already exists.");
        }
        System.out.println(pdmst.toString());
        Pdmst savePdmst = service.save(pdmst);
        return ResponseEntity.ok(savePdmst);
    }

    @PutMapping
    public ResponseEntity<?> updatePdmst(
            @RequestParam Integer zid,
            @RequestParam String xstaff,
            @RequestBody Pdmst updatedPdmst) {

        if (updatedPdmst.getXname() == null || updatedPdmst.getXname().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Validation failed: Name field is required.");
        }

        PdmstId id = new PdmstId(zid, xstaff);
        return service.findById(id)
                .map(existingPdmst -> {
                    updatedPdmst.setZid(zid);
                    updatedPdmst.setXstaff(xstaff);


                    // Update the entity
                    return ResponseEntity.ok(service.save(updatedPdmst));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping
    public ResponseEntity<Void> deletePdmst(
            @RequestParam int zid,
            @RequestParam String xstaff) {
        PdmstId id = new PdmstId(zid, xstaff);

        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Pdmst> searchPdmst(

            @RequestParam Integer zid,
            @RequestParam(required = false) String xstaff,
            @RequestParam(required = false) String xposition,
            @RequestParam(required = false) String xmobile

    ) {
        return service.findPdmst(zid,xstaff,xposition,xmobile);
    }

    @GetMapping("/searchtext")
    public List<Pdmst> search(
            @RequestParam("zid") String zid,
            @RequestParam("searchText") String searchText
    ) {
        return service.searchByText(zid, searchText);

    }
}

