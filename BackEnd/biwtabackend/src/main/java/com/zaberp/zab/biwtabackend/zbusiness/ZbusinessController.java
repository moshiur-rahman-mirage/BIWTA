package com.zaberp.zab.biwtabackend.zbusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zbusiness")
public class ZbusinessController {

    @Autowired
    private ZbusinessService zbusinessService;

    @GetMapping
    public List<Zbusiness> getAllZbusinesses() {
        return zbusinessService.getAllZbusinesses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Zbusiness> getZbusinessById(@PathVariable Integer id) {
        return zbusinessService.getZbusinessById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Zbusiness createZbusiness(@RequestBody Zbusiness zbusiness) {
        return zbusinessService.createZbusiness(zbusiness);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Zbusiness> updateZbusiness(
            @PathVariable Integer id,
            @RequestBody Zbusiness updatedZbusiness) {
        Zbusiness zbusiness = zbusinessService.updateZbusiness(id, updatedZbusiness);
        if (zbusiness != null) {
            return ResponseEntity.ok(zbusiness);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZbusiness(@PathVariable Integer id) {
        if (zbusinessService.getZbusinessById(id).isPresent()) {
            zbusinessService.deleteZbusiness(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

