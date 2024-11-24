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

import java.time.LocalDateTime;
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
        // Ensure that the composite key is set
        if (pdDependent.getZid() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: Composite key (zid, xstaff) is missing.");
        }

        // Validate zid
        if (pdDependent.getZid() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: 'zid' is mandatory.");
        }

        // Validate xstaff
        if (pdDependent.getXstaff() == null || pdDependent.getXstaff().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: Employee ID (xstaff) is missing.");
        }

        // Get the max xrow for the given zid and xstaff
        Integer maxXrow = service.getMaxXrowByZidAndXstaff(pdDependent.getZid(), pdDependent.getXstaff());

        // Set xrow to maxXrow + 1 for the new record
        pdDependent.setXrow(maxXrow + 1);

        // Save the entity
        PdDependent savedPdDependent = service.save(pdDependent);

        return ResponseEntity.ok(savedPdDependent);
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

        // Create the composite key for PdDependent
        PdDependentId id = new PdDependentId(zid, xstaff, xrow);

        // Find the existing record by its composite key
        return service.findById(id)
                .map(existingPdDependent -> {

                    updatedPdDependent.setZtime(existingPdDependent.getZtime()); // Retain existing ztime
                    updatedPdDependent.setZutime(LocalDateTime.now()); // Set the updated time
                    updatedPdDependent.setZauserid(existingPdDependent.getZauserid()); // Retain other necessary fields

                    // Save the updated entity and return the response
                    return ResponseEntity.ok(service.save(updatedPdDependent));
                })
                .orElse(ResponseEntity.notFound().build()); // Return not found if the entity doesn't exist
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
            @RequestParam("zid") Integer zid,
            @RequestParam("searchText") String searchText
    ) {
        return service.searchByText(zid, searchText);
    }



}

