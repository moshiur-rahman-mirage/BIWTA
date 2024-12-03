package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.PdsignatoryrptId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pdsignatoryrpt;
import com.zaberp.zab.biwtabackend.service.PdsignatoryrptService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pdsignatoryrpt")
public class PdsignatoryrptController {

    @Autowired
    private PdsignatoryrptService service;

    @GetMapping
    public ResponseEntity<List<Pdsignatoryrpt>> getAllRecords() {
        return ResponseEntity.ok(service.getAllRecords());
    }

    @GetMapping("/{zid}/{xtypetrn}")
    public ResponseEntity<Pdsignatoryrpt> getRecordById(
            @PathVariable Integer zid,
            @PathVariable String xtypetrn) {
        PdsignatoryrptId id = new PdsignatoryrptId(zid, xtypetrn);
        return ResponseEntity.ok(service.getRecordById(id));
    }

    @PostMapping
    public ResponseEntity<Pdsignatoryrpt> createRecord(@RequestBody Pdsignatoryrpt record) {
        return ResponseEntity.ok(service.saveRecord(record));
    }

    @PutMapping
    public ResponseEntity<Pdsignatoryrpt> updateRecord(@RequestBody Pdsignatoryrpt record) {
        return ResponseEntity.ok(service.saveRecord(record));
    }


    @Transactional
    @PatchMapping("/{zid}/{xrow}")
    public ResponseEntity<String> updatePdsignatory(
            @PathVariable int zid,
            @PathVariable int xrow,
            @RequestBody Map<String, Object> updates) {
        List<String> excludeColumns = List.of("ztime", "zauserid");

        boolean success = service.updatePdsignatoryrpt(zid, xrow, updates, excludeColumns);

        if (success) {
            return ResponseEntity.ok("Update successful.");
        } else {
            return ResponseEntity.badRequest().body("No records were updated. Please check the input.");
        }
    }



    @GetMapping("/signatory/{zid}")
    public Page<Pdsignatoryrpt> getItems(
            @PathVariable int zid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "xrow") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return service.getSignatoryWithPaginationAndSorting(zid,page, size, sortBy, ascending);
    }


    @GetMapping("/search")
    public List<Pdsignatoryrpt> search(
            @RequestParam("zid") int zid,
            @RequestParam("text") String searchText
    ) {
        return service.searchByText(zid, searchText);

    }

    @DeleteMapping("/{zid}/{xtypetrn}")
    public ResponseEntity<String> deleteRecord(
            @PathVariable Integer zid,
            @PathVariable String xtypetrn) {
        PdsignatoryrptId id = new PdsignatoryrptId(zid, xtypetrn);
        service.deleteRecord(id);
        return ResponseEntity.ok("Record deleted successfully");
    }
}

