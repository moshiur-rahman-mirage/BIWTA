package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.service.PdmstService;
import com.zaberp.zab.biwtabackend.service.PrimaryKeyService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pdmst")
public class PdmstController {

    private final PdmstService service;
    private final PrimaryKeyService primaryKeyService;
    public PdmstController(PdmstService service, PrimaryKeyService primaryKeyService) {
        this.service = service;
        this.primaryKeyService = primaryKeyService;
    }

    @PostMapping
    public ResponseEntity<?> createPdmst(@RequestBody Pdmst pdmst) {
//        if (service.existsByZidAndXstaffAndXposition(pdmst.getZid(), pdmst.getXstaff(),pdmst.getXposition())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Validation failed: A Employee with the same staff id and position already exists.");
//        }

        if (service.existsByZidAndXmobile(pdmst.getZid(), pdmst.getXmobile())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: A Employee with the same mobile number already exists.");
        }
//        if (service.existsByZidAndXposition(pdmst.getZid(), pdmst.getXposition())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Validation failed: A Employee with the same position id already exists.");
//        }

        String generatedKey = primaryKeyService.getGeneratedPrimaryKey(pdmst.getZid(), "Staff ID", "EID-",5);
        System.out.println(generatedKey);
//        String numericPart = generatedKey.substring(4);
//        String newId = "1" + numericPart;
        pdmst.setXstaff(generatedKey);
        pdmst.setZtime(LocalDateTime.now());
        pdmst.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
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
                    // Copy properties from updatedPdmst to existingPdmst
                    BeanUtils.copyProperties(updatedPdmst, existingPdmst,
                            "zid","xstaff","zauserid","ztime"); // Exclude fields that should not be updated
                    existingPdmst.setZuuserid(SecurityContextHolder.getContext().getAuthentication().getName());
                    existingPdmst.setZutime(LocalDateTime.now());
                    // Save the updated entity (triggers @PreUpdate)
                    return ResponseEntity.ok(service.save(existingPdmst));
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

