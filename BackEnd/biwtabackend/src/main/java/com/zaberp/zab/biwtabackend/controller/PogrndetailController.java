package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.dto.PogrndetailXitemdto;
import com.zaberp.zab.biwtabackend.id.PogrndetailId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pogrndetail;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.service.PogrndetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pogrndetails")
public class PogrndetailController {

    @Autowired
    private PogrndetailService service;

    @PostMapping
    public ResponseEntity<Pogrndetail> save(@RequestBody Pogrndetail detail) {

        Pogrndetail savedDetail = service.save(detail);
        System.out.println(savedDetail);
        return ResponseEntity.ok(savedDetail);
    }

    @PutMapping("")
    public ResponseEntity<Pogrndetail> updateByZidAndXgrnnum(
            @RequestParam int zid,
            @RequestParam String xgrnnum,
            @RequestParam int xrow,
            @RequestBody Pogrndetail detail) {
        if (detail.getZid() != zid || !detail.getXgrnnum().equals(xgrnnum) || detail.getXrow()!=xrow) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.updateByZidAndXgrnnumAndXrow(detail));
    }


    @DeleteMapping("")
    public ResponseEntity<Void> deleteDetail(@RequestParam Integer zid,
                                             @RequestParam String xgrnnum,
                                             @RequestParam Integer xrow) {
        PogrndetailId id = new PogrndetailId(zid, xgrnnum, xrow);
        service.deleteDetail(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{zid}/{xgrnnum}")
    public Page<PogrndetailXitemdto> getPogrndetailXitems(
            @PathVariable("zid") int zid,
            @PathVariable("xgrnnum") String xgrnnum,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return service.getPogrndetailXitems(zid,xgrnnum, page, size);
    }


}
