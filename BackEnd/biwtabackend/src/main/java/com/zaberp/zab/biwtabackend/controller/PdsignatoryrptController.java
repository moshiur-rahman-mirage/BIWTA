package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.PdsignatoryrptId;
import com.zaberp.zab.biwtabackend.model.Pdsignatoryrpt;
import com.zaberp.zab.biwtabackend.service.PdsignatoryrptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{zid}/{xtypetrn}")
    public ResponseEntity<String> deleteRecord(
            @PathVariable Integer zid,
            @PathVariable String xtypetrn) {
        PdsignatoryrptId id = new PdsignatoryrptId(zid, xtypetrn);
        service.deleteRecord(id);
        return ResponseEntity.ok("Record deleted successfully");
    }
}

