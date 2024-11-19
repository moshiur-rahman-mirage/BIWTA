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
    public ResponseEntity<Xcodes> updateXcodes(
            @RequestParam Integer zid,  // Mandatory and immutable
            @RequestParam String xtype, // Optional
            @RequestParam String xcode, // Optional
            @RequestBody Xcodes updatedXcodes) {

        XcodesId id = new XcodesId(zid, xtype, xcode);

        // Check if the record exists
        return service.findById(id)
                .map(existingXcodes -> {
                    // Preserve zid (immutable)
                    updatedXcodes.setZid(existingXcodes.getZid());
                    updatedXcodes.setXtype(existingXcodes.getXtype());
                    updatedXcodes.setXcode(existingXcodes.getXcode());

                    // Save updated record
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

