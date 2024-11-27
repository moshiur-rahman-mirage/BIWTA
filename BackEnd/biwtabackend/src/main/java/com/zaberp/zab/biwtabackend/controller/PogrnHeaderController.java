package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.service.PogrnHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pogrnheader")
public class PogrnHeaderController {

    @Autowired
    private PogrnHeaderService service;

    @GetMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<Pogrnheader> getByZidAndXgrnnum(@PathVariable int zid, @PathVariable String xgrnnum) {
        Optional<Pogrnheader> entity = service.getByZidAndXgrnnum(zid, xgrnnum);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping()
    public Page<PogrnheaderXcusdto> getItems(
            @RequestParam int zid,
            @RequestParam(defaultValue = "Open") String xstatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "xgrnnum") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return service.findPogrnWithSupplier(zid,xstatus,page, size, sortBy, ascending);
    }

    @PostMapping
    public ResponseEntity<Pogrnheader> createItem(@RequestBody Pogrnheader pogrnheader) {
        Pogrnheader createdGrn = service.createGrn(pogrnheader);
        return ResponseEntity.ok(createdGrn);
    }

    @PutMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<Pogrnheader> updateByZidAndXgrnnum(
            @PathVariable int zid,
            @PathVariable String xgrnnum,
            @RequestBody Pogrnheader entity) {
        if (entity.getZid() != zid || !entity.getXgrnnum().equals(xgrnnum)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.updateByZidAndXgrnnum(entity));
    }

//    @DeleteMapping("/{zid}/{xgrnnum}")
//    public ResponseEntity<Void> deleteByZidAndXgrnnum(@PathVariable int zid, @PathVariable String xgrnnum) {
//        service.deleteByZidAndXgrnnum(zid, xgrnnum);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<Void> deleteXgrnnum(
            @PathVariable int zid,
            @PathVariable String xgrnnum) {
        PogrnHeaderId id = new PogrnHeaderId(zid, xgrnnum);
        service.deleteGrn(id);
        return ResponseEntity.noContent().build();
    }
}
