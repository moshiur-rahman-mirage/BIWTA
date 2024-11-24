package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.id.XcodesId;
import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.service.XcodesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xcodes")
public class XcodesController {

    private final XcodesService service;

    public XcodesController(XcodesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createXcodes(@RequestBody Xcodes xcodes) {
        if (service.existsByZidAndXtypeAndXcode(xcodes.getZid(), xcodes.getXtype(),xcodes.getXcode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: A code with the same type already exists.");
        }
        Xcodes savedXcodes = service.save(xcodes);
        return ResponseEntity.ok(savedXcodes);
    }

    @PutMapping
    public ResponseEntity<?> updateXcodes(
            @RequestParam Integer zid,
            @RequestParam String xtype,
            @RequestParam String xcode,
            @RequestBody Xcodes updatedXcodes) {

//        if (updatedXcodes.getXlong() == null || updatedXcodes.getXlong().isBlank()) {
//            return ResponseEntity.badRequest()
//                    .body("Validation failed: Name field is required.");
//        }

        XcodesId id = new XcodesId(zid, xtype, xcode);
        return service.findById(id)
                .map(existingXcodes -> {
                    // Ensure zid, xtype, and xcode remain unchanged
                    updatedXcodes.setZid(zid);
                    updatedXcodes.setXtype(xtype);
                    updatedXcodes.setXcode(xcode);

                    // Update the entity
                    return ResponseEntity.ok(service.save(updatedXcodes));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteXcodes(
            @RequestParam int zid,
            @RequestParam String xtype,
            @RequestParam String xcode) {
        XcodesId id = new XcodesId(zid, xtype, xcode);

        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Xcodes> searchXcodes(
            @RequestParam Integer zid,
            @RequestParam(required = false) String xtype,
            @RequestParam(required = false) String xcode) {
        return service.findXcodes(zid, xtype, xcode);
    }

    @GetMapping("/searchtext")
    public List<Xcodes> search(
            @RequestParam("zid") String zid,
            @RequestParam("xtype") String xtype,
            @RequestParam("searchText") String searchText
    ) {
        return service.searchByText(zid,xtype, searchText);

    }


    @GetMapping("/active")
    public List<Xcodes> findActiveXcodesByZidAndXtype(
            @RequestParam("zid") String zid,
            @RequestParam("xtype") String xtype
    ) {
        return service.findActiveXcodesByZidAndXtype(zid,xtype);

    }
}

