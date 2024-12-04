package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.service.PdmstService;
import com.zaberp.zab.biwtabackend.service.PrimaryKeyService;
import jakarta.persistence.EntityNotFoundException;
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
        Pdmst savePdmst = service.createPdmst(pdmst);
        return ResponseEntity.ok(savePdmst);
    }


    @PutMapping
    public ResponseEntity<?> updatePdmst(
            @RequestParam Integer zid,
            @RequestParam String xstaff,
            @RequestBody Pdmst updatedPdmst) {

        try {
            Pdmst updatedEntity = service.updatePdmst(zid, xstaff, updatedPdmst);
            return ResponseEntity.ok(updatedEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping("approver/{zid}")
    public List<Pdmst> findbyZidAndZactive(
            @PathVariable int zid
    ) {
        return service.findByZidAndZactive(zid,"1");
    }

    @GetMapping("/searchtext")
    public List<Pdmst> search(
            @RequestParam("zid") String zid,
            @RequestParam("searchText") String searchText
    ) {
        return service.searchByText(zid, searchText);

    }
}

