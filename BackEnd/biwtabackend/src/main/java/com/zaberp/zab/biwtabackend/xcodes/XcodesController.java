package com.zaberp.zab.biwtabackend.xcodes;


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
    public Xcodes createXcodes(@RequestBody Xcodes xcodes) {
        return service.save(xcodes);
    }

    @PutMapping
    public ResponseEntity<?> updateXcodes(
            @RequestParam Integer zid,
            @RequestParam String xtype,
            @RequestParam String xcode,
            @RequestBody Xcodes updatedXcodes) {

        if (updatedXcodes.getXlong() == null || updatedXcodes.getXlong().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Validation failed: 'xlong' field is required.");
        }

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
}

