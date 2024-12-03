package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.dto.ConfirmGrnDto;
import com.zaberp.zab.biwtabackend.dto.ConfirmImtorDto;
import com.zaberp.zab.biwtabackend.dto.ImtorDto;
import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import com.zaberp.zab.biwtabackend.service.ImtorheaderService;
import com.zaberp.zab.biwtabackend.util.SearchUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
            @RequestParam(defaultValue ="") String xtrn,
            @RequestParam(defaultValue = "") String user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "xtornum") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return service.findImtorWithZidAndStatusAndUser(zid,xstatus,xtrn,user,page, size, sortBy, ascending);
    }


    @GetMapping("/confirmed")
    public Page<ImtorDto> getAppvoedItems(
            @RequestParam int zid,
            @RequestParam(defaultValue ="") String xtrn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "xtornum") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return service.findImtorWithZidAndStatus(zid,xtrn,page, size, sortBy, ascending);
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

    @Transactional
    @PatchMapping("/{zid}/{xtornum}")
    public ResponseEntity<String> updateImtorheader(
            @PathVariable int zid,
            @PathVariable String xtornum,
            @RequestBody Map<String, Object> updates) {


        List<String> excludeColumns = List.of("xfwhdesc","xtwhdesc","xdate", "ztime", "zauserid", "xstatustor");

        // Call the service method
        boolean success = service.updateImtorheader(zid, xtornum, updates, excludeColumns);

        if (success) {
            return ResponseEntity.ok("Update successful.");
        } else {
            return ResponseEntity.badRequest().body("No records were updated. Please check the input.");
        }
    }

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
            @RequestParam(value = "action", required = false, defaultValue = "") String action,
            @RequestParam("text") String searchText
    ) {
        return service.searchByText(zid, action, searchText);
    }


    @GetMapping("/All")
    public List<ImtorDto> search(
            @RequestParam("zid") int zid,
            @RequestParam("text") String searchText
    ) {
        return service.searchByText(zid,  searchText);
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


    @PostMapping("/confirmsr")
    public String confirmSR(@RequestBody ConfirmImtorDto imtor){
        int zid = imtor.getZid();
        String zemail=imtor.getUser();
        String xposition=imtor.getXposition();
        String xtornum = imtor.getXtornum();
        Date xdate = imtor.getXdate();
        String xwh = imtor.getXfwh();
        String xtwh=imtor.getXtwh();
        String xstatustor=imtor.getXstatustor();
        int xlen=imtor.getLen();
        return service.confirmImtor(zid, zemail,xposition,xtornum,xdate,xwh,xtwh,xstatustor,"Transfer",xlen);
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

