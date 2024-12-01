package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.dto.ConfirmGrnDto;
import com.zaberp.zab.biwtabackend.dto.ConfirmImtorDto;
import com.zaberp.zab.biwtabackend.dto.ImtorDto;
import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import com.zaberp.zab.biwtabackend.service.ImtorheaderService;
import com.zaberp.zab.biwtabackend.util.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/imtorheader")
public class ImtorheaderController {

    @Autowired
    private ImtorheaderService service;

    @GetMapping("/{zid}/{xtornum}")
    public ResponseEntity<Imtorheader> getByZidAndXtornum(@PathVariable int zid, @PathVariable String xtornum) {
        Optional<Imtorheader> entity = service.getByZidAndXtornum(zid, xtornum);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping()
    public Page<ImtorDto> getItems(
            @RequestParam int zid,
            @RequestParam(defaultValue = "Open") String xstatus,
            @RequestParam(defaultValue = "") String user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "xtornum") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return service.findImtorWithZidAndStatusAndUser(zid,xstatus,user,page, size, sortBy, ascending);
    }

    @PostMapping()
    public ResponseEntity<Imtorheader> createItem(
            @RequestBody Imtorheader imtorheader,
            @RequestParam("action") String action) {
        Imtorheader createDam = service.createTransaction(imtorheader,action);
        return ResponseEntity.ok(createDam);
    }

    @PutMapping("/{zid}/{xtornum}")
    public ResponseEntity<Imtorheader> updateByZidAndXtornum(
            @PathVariable int zid,
            @PathVariable String xtornum,
            @RequestBody Imtorheader entity) {
        if (entity.getZid() != zid || !entity.getXtornum().equals(xtornum)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.updateByZidAndXtornum(entity));
    }

//    @DeleteMapping("/{zid}/{xgrnnum}")
//    public ResponseEntity<Void> deleteByZidAndXgrnnum(@PathVariable int zid, @PathVariable String xgrnnum) {
//        service.deleteByZidAndXgrnnum(zid, xgrnnum);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/{zid}/{xtornum}")
    public ResponseEntity<Void> delteXtornum(
            @PathVariable int zid,
            @PathVariable String xtornum) {
        ImtorheaderId id = new ImtorheaderId(zid, xtornum);
        service.deleteImtor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<ImtorDto> search(
            @RequestParam("zid") int zid,
            @RequestParam("action") String action,
            @RequestParam("text") String searchText
    ) {
        return service.searchByText(zid, action, searchText);
    }

    @PostMapping("/confirmDam")
    public String confirmDamage(@RequestBody ConfirmImtorDto imtor){
        int zid = imtor.getZid();
        String zemail=imtor.getUser();
        String xposition=imtor.getXposition();
        String xtornum = imtor.getXtornum();
        Date xdate = imtor.getXdate();
        String xwh = imtor.getXfwh();
        String xtwh=imtor.getXtwh();
        String xstatustor=imtor.getXstatustor();
        int xlen=imtor.getLen();
        return service.confirmImtor(zid, zemail,xposition,xtornum,xdate,xwh,xtwh,"Checked","Transfer",xlen);
    }

    @PostMapping("/checksr")
    public String checkSR(@RequestBody ConfirmImtorDto imtor){
        int zid = imtor.getZid();
        String zemail=imtor.getUser();
        String xtornum = imtor.getXtornum();
        Date xdate = imtor.getXdate();
        String xfwh = imtor.getXfwh();
        String xstatustor=imtor.getXstatustor();
        return service.checkSR(zid, zemail,xtornum,xdate,xfwh,xstatustor);
    }
}

