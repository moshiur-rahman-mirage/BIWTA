package com.zaberp.zab.biwtabackend.controller;



import com.zaberp.zab.biwtabackend.id.XtrnId;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.id.XcodesId;
import com.zaberp.zab.biwtabackend.model.Xtrn;
import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.service.XcodesService;
import com.zaberp.zab.biwtabackend.service.XtrnService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xtrn")
public class XtrnController {

    private final XtrnService service;

    public XtrnController(XtrnService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createXtrn(@RequestBody Xtrn xtrn) {
        Xtrn savedXtrn = service.saveRecord(xtrn);
        return ResponseEntity.ok(savedXtrn);
    }

    @PutMapping
    public ResponseEntity<?> updateXtrn(
            @RequestParam Integer zid,
            @RequestParam String xtypetrn,
            @RequestParam String xtrn,
            @RequestBody Xtrn updatedXtrn) {



        XtrnId id = new XtrnId(zid, xtypetrn, xtrn);
        return service.getRecordById(id)
                .map(existingXcodes -> {
                    // Ensure zid, xtype, and xcode remain unchanged
                    updatedXtrn.setZid(zid);
                    updatedXtrn.setXtypetrn(xtypetrn);
                    updatedXtrn.setXtrn(xtrn);

                    // Update the entity
                    return ResponseEntity.ok(service.saveRecord(updatedXtrn));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteXtrn(
            @RequestParam int zid,
            @RequestParam String xtypetrn,
            @RequestParam String xtrn) {
        XtrnId id = new XtrnId(zid, xtypetrn, xtrn);

        if (service.getRecordById(id).isPresent()) {
            service.deleteRecord(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Xtrn> searchXtrn(
            @RequestParam Integer zid,
            @RequestParam(required = false) String xtypetrn,
            @RequestParam(required = false) String xtrn) {
        return service.findXtrn(zid, xtypetrn, xtrn);
    }



    @GetMapping("/active")
    public List<Xtrn> findActiveXtrnByZidAndXtypetrn(
            @RequestParam("zid") int zid,
            @RequestParam("xtypetrn") String xtypetrn
    ) {
        return service.findActiveXcodesByZidAndXtype(zid,xtypetrn);

    }
}


