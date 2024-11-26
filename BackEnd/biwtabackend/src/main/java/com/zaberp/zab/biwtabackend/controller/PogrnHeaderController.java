package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.model.PogrnHeader;
import com.zaberp.zab.biwtabackend.service.PogrnHeaderService;
import com.zaberp.zab.biwtabackend.service.PrimaryKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pogrnheader")
public class PogrnHeaderController {

    @Autowired
    private PogrnHeaderService service;

    @GetMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<PogrnHeader> getByZidAndXgrnnum(@PathVariable int zid, @PathVariable String xgrnnum) {
        Optional<PogrnHeader> entity = service.getByZidAndXgrnnum(zid, xgrnnum);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<PogrnHeader> updateByZidAndXgrnnum(
            @PathVariable int zid,
            @PathVariable String xgrnnum,
            @RequestBody PogrnHeader entity) {
        if (entity.getZid() != zid || !entity.getXgrnnum().equals(xgrnnum)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.updateByZidAndXgrnnum(entity));
    }

    @DeleteMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<Void> deleteByZidAndXgrnnum(@PathVariable int zid, @PathVariable String xgrnnum) {
        service.deleteByZidAndXgrnnum(zid, xgrnnum);
        return ResponseEntity.noContent().build();
    }
}
