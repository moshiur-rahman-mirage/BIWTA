package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.service.PogrnHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
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



    @GetMapping("/{zid}")
    public ResponseEntity<List<Pogrnheader>> getGrnByZid(
            @PathVariable int zid) {
        List<Pogrnheader> grnList = service.getByZid(zid);
        if (grnList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(grnList);
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
