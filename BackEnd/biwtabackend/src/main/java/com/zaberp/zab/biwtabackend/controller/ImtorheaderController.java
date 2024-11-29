package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import com.zaberp.zab.biwtabackend.service.ImtorheaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imtorheader")
public class ImtorheaderController {

    @Autowired
    private ImtorheaderService service;

    @GetMapping
    public List<Imtorheader> getAll() {
        return service.findAll();
    }

    @GetMapping("/{zid}/{xtornum}")
    public ResponseEntity<Imtorheader> getById(@PathVariable int zid, @PathVariable String xtornum) {
        ImtorheaderId id = new ImtorheaderId(zid, xtornum);
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Imtorheader create(@RequestBody Imtorheader imtorheader) {
        return service.save(imtorheader);
    }

    @PutMapping
    public Imtorheader update(@RequestBody Imtorheader imtorheader) {
        return service.save(imtorheader);
    }

    @DeleteMapping("/{zid}/{xtornum}")
    public ResponseEntity<Void> deleteById(@PathVariable int zid, @PathVariable String xtornum) {
        ImtorheaderId id = new ImtorheaderId(zid, xtornum);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

