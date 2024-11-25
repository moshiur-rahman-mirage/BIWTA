package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.id.CacusId;
import com.zaberp.zab.biwtabackend.model.Cacus;
import com.zaberp.zab.biwtabackend.service.CacusService;
import com.zaberp.zab.biwtabackend.service.PrimaryKeyService;
import com.zaberp.zab.biwtabackend.util.ApplicationContextData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cacus")
public class CacusController {

    @Autowired
    private CacusService cacusService;

    private final PrimaryKeyService primaryKeyService;

    public CacusController(PrimaryKeyService primaryKeyService) {
        this.primaryKeyService = primaryKeyService;
    }

    @GetMapping("/{zid}/type/{xtype}")
    public ResponseEntity<List<Cacus>> getCacusByZidAndXtype(
            @PathVariable int zid,
            @PathVariable String xtype) {
        List<Cacus> cacusList = cacusService.getCacusByZidAndXtype(zid, xtype);
        if (cacusList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cacusList);
    }

    @GetMapping("/{zid}/id/{xcus}")
    public ResponseEntity<Cacus> getCacusById(@PathVariable int zid, @PathVariable String xcus) {
        CacusId id = new CacusId(zid,xcus);
        id.setZid(zid);
        id.setXcus(xcus);
        return cacusService.getCacusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Autowired
    private ApplicationContextData appContext;
    @PostMapping
    public Cacus createCacus(@RequestBody Cacus cacus) {
        System.out.println("app context zid");
        System.out.println(cacus.getZid());
        String generatedKey = primaryKeyService.getGeneratedPrimaryKey(cacus.getZid(), "Party Code", "PAR-",6);
        cacus.setXcus(generatedKey);
        cacus.setZtime(LocalDateTime.now());
        cacus.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
        return cacusService.save(cacus);
    }


    @PutMapping
    public ResponseEntity<?> updateCacus(
            @RequestParam int zid,
            @RequestParam String xcus,
            @RequestBody Cacus updatedCacus) {

        if (updatedCacus.getXorg() == null || updatedCacus.getXorg().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Validation failed: Name field is required.");
        }

        CacusId id = new CacusId(zid, xcus);

        return cacusService.getCacusById(id)
                .map(existingCacus -> {
                    System.out.println(zid);
                    // Copy properties from updatedPdmst to existingPdmst
                    BeanUtils.copyProperties(updatedCacus, existingCacus,
                            "zid","xstaff","zauserid","ztime");
                    existingCacus.setZuuserid(SecurityContextHolder.getContext().getAuthentication().getName());
                    existingCacus.setZutime(LocalDateTime.now());
                    // Save the updated entity (triggers @PreUpdate)
                    return ResponseEntity.ok(cacusService.save(existingCacus));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{zid}/{xcus}")
    public ResponseEntity<Void> deleteCacus(@PathVariable int zid, @PathVariable String xcus) {
        CacusId id = new CacusId(zid,xcus);
        id.setZid(zid);
        id.setXcus(xcus);
        cacusService.deleteCacus(id);
        return ResponseEntity.noContent().build();
    }
}
